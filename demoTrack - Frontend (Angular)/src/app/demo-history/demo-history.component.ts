import {Component, ElementRef, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {MatSort, MatPaginator, MatTableDataSource} from '@angular/material';
import {DemoRequest} from '../_models/demo-request';
import * as XLSX from 'xlsx';
import * as moment from 'moment';
import {AuthenticationService} from '../_services/authentication.service';
import {DataService} from '../_services/data.service';
import {User} from '../_models/user';
import {Role} from '../_models/role.enum';
import {MatDialog} from '@angular/material/dialog';
import {RequestDetailsDialogComponent} from '../request-details-dialog/request-details-dialog.component';
import saveAs from 'file-saver';

@Component({
  selector: 'app-demo-history',
  templateUrl: './demo-history.component.html',
  styleUrls: ['./demo-history.component.css']
})
export class DemoHistoryComponent implements OnInit {
  loading: boolean;
  public currentUser: User;
  public requestList: DemoRequest[] = [];
  displayedColumns: string[] = [];
  dataSource = new MatTableDataSource<DemoRequest>();
  @ViewChild('TABLE', {static: true}) table: ElementRef;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(private authenticationService: AuthenticationService,
              private dataService: DataService,
              public dialog: MatDialog) {
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
    if (this.currentUser.role === Role.Sales) {
      this.displayedColumns = ['requestNumber', 'requestTimestamp',
        'customerName', 'customerEmail', 'customerLocation', 'customerBusinessLine', 'contactPersonName', 'contactPersonEmail',
        'productGroup', 'productLine', 'product',
        'demoSite', 'demoDate', 'demoSlot', 'numberOfGuests', 'demoExpectation',
        'aseDressCode', 'aseTechnicalKnowledgeRating', 'aseClinicalKnowledgeRating', 'demoSitePrepared', 'aseCustomerHandlingRating',
        'siteAmbianceRating', 'productRating', 'customerConvinced', 'feedback', 'comment', 'status', 'authority'];
    } else {
      this.displayedColumns = ['requestNumber', 'requestTimestamp', 'requesterUserId', 'requesterName',
        'customerName', 'customerEmail', 'customerLocation', 'customerBusinessLine', 'contactPersonName', 'contactPersonEmail',
        'productGroup', 'productLine', 'product',
        'demoSite', 'demoDate', 'demoSlot', 'numberOfGuests', 'demoExpectation',
        'aseDressCode', 'aseTechnicalKnowledgeRating', 'aseClinicalKnowledgeRating', 'demoSitePrepared', 'aseCustomerHandlingRating',
        'siteAmbianceRating', 'productRating', 'customerConvinced', 'feedback', 'comment', 'status', 'authority'];
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

  getRequests() {
    this.dataService.getAllDemoRequests().subscribe(
      data => {
        this.requestList = data;
        if (this.currentUser.role === Role.Admin) {
          this.requestList = this.requestList.filter(request => request.status === 'Rejected' || request.status === 'Completed');
        } else if (this.currentUser.role === Role.Approver) {
          this.requestList = this.requestList.filter(request => request.status === 'Rejected' || request.status === 'Completed' || request.status === 'Approved');
        } else {
          this.requestList = this.requestList.filter(request => request.requesterUserId.toString() === this.currentUser.username && (request.status === 'Rejected' || request.status === 'Completed'));
        }
        this.dataSource = new MatTableDataSource<DemoRequest>(this.requestList);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        this.paginator._changePageSize(10000);
        this.loading = false;
      }
    );
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  ExportTOExcel() {
    const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet(this.dataSource.filteredData);
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
    const now = moment().format('YYYY-MM-DD HH:mm:ss');
    /* save to file */
    // XLSX.writeFile(wb, 'Demo History Report ' + now + '.xlsx');
    const file = XLSX.write(wb, {bookType: 'xlsx', bookSST: false, type: 'array'});
    saveAs(new Blob([file], {type: 'application/octet-stream'}), 'Demo History Report ' + now + '.xlsx');
  }
  openDialog(id: number): void {
    const dialogRef = this.dialog.open(RequestDetailsDialogComponent, {
      data: this.requestList.find(el => el.id === id),
    });

    dialogRef.afterClosed().subscribe(result => {
    });
  }

}
