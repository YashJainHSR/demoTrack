import {Component, ElementRef, OnInit, ViewChild, Inject, ViewEncapsulation} from '@angular/core';
import {MatSort, MatPaginator, MatTableDataSource} from '@angular/material';
import {DemoRequest} from '../_models/demo-request';
import * as XLSX from 'xlsx';
import * as moment from 'moment';
import {AuthenticationService} from '../_services/authentication.service';
import {DataService} from '../_services/data.service';
import {User} from '../_models/user';
import {Role} from '../_models/role.enum';
import {Router} from '@angular/router';
import {MailService} from '../_services/mail.service';
import {CommonMethodsService} from '../_services/common-methods.service';
import {MatDialog} from '@angular/material/dialog';
import {RequestDetailsDialogComponent} from '../request-details-dialog/request-details-dialog.component';
import saveAs from 'file-saver';

@Component({
  selector: 'app-demo-feedback',
  templateUrl: './demo-requests.component.html',
  styleUrls: ['./demo-requests.component.css']
})
export class DemoRequestsComponent implements OnInit {
  loading: boolean;
  public currentUser: User;
  public requestList: DemoRequest[] = [];
  displayedColumns: string[] = [];
  public saveInProgress: boolean;
  dataSource = new MatTableDataSource<DemoRequest>();
  @ViewChild('TABLE', {static: true}) table: ElementRef;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(private authenticationService: AuthenticationService,
              private dataService: DataService,
              private mailService: MailService,
              private commonMethods: CommonMethodsService,
              private router: Router,
              public dialog: MatDialog) {
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
    if (this.currentUser.role === Role.Sales) {
      this.displayedColumns = ['requestNumber', 'customerName', 'customerLocation'
        , 'productGroup', 'productLine', 'product', 'demoSite', 'demoDate', 'demoSlot', 'status'];
    } else if (this.currentUser.role === Role.Admin) {
      this.displayedColumns = ['requestNumber', 'requesterUserId', 'customerName', 'customerLocation'
        , 'productGroup', 'productLine', 'product', 'demoSite', 'demoDate', 'demoSlot', 'status'];
    } else {
      this.displayedColumns = ['requestNumber', 'requesterUserId', 'customerName', 'customerLocation'
        , 'productGroup', 'productLine', 'product', 'demoSite', 'demoDate', 'demoSlot'];
    }
  }

  ngOnInit() {
    this.loading = true;
    document.querySelector('body').setAttribute('style', 'background-color: gainsboro;');
    const el = document.querySelector('mat-table') as HTMLElement;
    const top = document.querySelector('#topNav') as HTMLElement;
    const dash = document.querySelector('#dash') as HTMLElement;
    const wrapper = document.createElement('div');
    const topHeight: number = top.offsetHeight;
    const dashHeight: number = dash.offsetHeight;
    wrapper.setAttribute('id', 'mat-table-wrapper');
    wrapper.style.overflow = 'auto';
    wrapper.style.height = 'calc(100vh - (' + topHeight.toString() + 'px + ' + dashHeight.toString() + 'px + 100px))';
    el.parentNode.insertBefore(wrapper, el);
    wrapper.appendChild(el);
    this.getRequests();
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  getRequests() {
    this.dataService.getAllDemoRequests().subscribe(
      data => {
        this.requestList = data;
        if (this.currentUser.role === Role.Admin) {
          this.requestList = this.requestList.filter(request => (request.status === 'Submitted' || request.status === 'Approved'));
        } else if (this.currentUser.role === Role.Approver) {
          this.requestList = this.requestList.filter(request => (request.status === 'Submitted'));
        } else {
          // tslint:disable-next-line:max-line-length
          this.requestList = this.requestList.filter(request => request.requesterUserId.toString() === this.currentUser.username && (request.status === 'Submitted' || request.status === 'Approved'));
        }
        this.dataSource = new MatTableDataSource<DemoRequest>(this.requestList);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        this.paginator._changePageSize(10000);
        this.loading = false;
      }
    );
  }

  ExportTOExcel() {
    const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet(this.dataSource.filteredData);
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
    const now = moment().format('YYYY-MM-DD HH:mm:ss');
    /* save to file */
    // const file = XLSX.write(wb, 'Report ' + now + '.xlsx');
    const file = XLSX.write(wb, {bookType: 'xlsx', bookSST: false, type: 'array'});
    saveAs(new Blob([file], {type: 'application/octet-stream'}), 'Report ' + now + '.xlsx');
  }

  provideFeedback(requestNumber: string) {
    this.router.navigate(['feedback/' + requestNumber]).then(r => {});
  }

  approve(requestNumber: string) {
    this.saveInProgress = true;
    document.getElementById('overlay').style.display = 'flex';
    const request = this.requestList.find(el => el.requestNumber === requestNumber);
    request.status = 'Approved';
    request.authority = this.currentUser.username;
    this.dataService.updateDemoRequests(request)
      .subscribe(
        data => {
          this.mailService.sendMailForApproval(data).subscribe(() => {
          });
          this.ngOnInit();
          this.saveInProgress = false;
          document.getElementById('overlay').style.display = 'none';
        }
      );
  }

  reject(requestNumber: string) {
    this.saveInProgress = true;
    document.getElementById('overlay').style.display = 'flex';
    const request = this.requestList.find(el => el.requestNumber === requestNumber);
    request.status = 'Rejected';
    request.aseDressCode = 'NA';
    request.aseTechnicalKnowledgeRating = 0;
    request.aseClinicalKnowledgeRating = 0;
    request.demoSitePrepared = 'NA';
    request.aseCustomerHandlingRating = 0;
    request.siteAmbianceRating = 0;
    request.productRating = 0;
    request.customerConvinced = 'NA';
    request.feedback = 'NA';
    request.comment = 'NA';
    request.authority = this.currentUser.username;
    this.dataService.updateDemoRequests(request)
      .subscribe(
        data => {
          // tslint:disable-next-line:no-shadowed-variable
          this.mailService.sendMailForApproval(data).subscribe(data => {
          });
          this.ngOnInit();
          this.saveInProgress = false;
          document.getElementById('overlay').style.display = 'none';
        }
      );
  }
  openDialog(id: number): void {
    const dialogRef = this.dialog.open(RequestDetailsDialogComponent, {
      data: this.requestList.find(el => el.id === id),
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 'feedback') {
        this.provideFeedback(this.requestList.find(el => el.id === id).requestNumber);
      } else if (result === 'approve') {
        this.approve(this.requestList.find(el => el.id === id).requestNumber);
      } else if (result === 'reject') {
        this.reject(this.requestList.find(el => el.id === id).requestNumber);
      } else {}
    });
  }
}
