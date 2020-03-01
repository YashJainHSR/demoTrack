import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthenticationService} from '../_services/authentication.service';
import {DataService} from '../_services/data.service';
import {User} from '../_models/user';
import {Product} from '../_models/product-details';
import { ToastrService } from 'ngx-toastr';
import {Router} from '@angular/router';
import * as moment from 'moment';
import {MailService} from '../_services/mail.service';
import {CommonMethodsService} from '../_services/common-methods.service';
import {DemoRequest} from '../_models/demo-request';

@Component({
  selector: 'app-demo-request',
  templateUrl: './demo-request-form.component.html',
  styleUrls: ['./demo-request-form.component.css']
})
export class DemoRequestFormComponent implements OnInit {
  public demoRequest: FormGroup;
  public currentUser: User;
  public productList: Product[] = [];
  public productGroups: string[];
  public productLines: string[];
  public products: string[];
  public productListFiltered: Product[];
  public previousRequests: DemoRequest[];
  public saveInProgress: boolean;
  public error: string;
  public overlayMessage: string;
  constructor(private formBuilder: FormBuilder,
              private authenticationService: AuthenticationService,
              private dataService: DataService,
              private mailService: MailService,
              private commonMethods: CommonMethodsService,
              private toastr: ToastrService,
              private router: Router) {
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
    this.demoRequest = this.formBuilder.group(
      {
        requestNumber: new FormControl(''),
        requestTimestamp: new FormControl(''),
        requesterUserId: new FormControl(''),
        requesterName: new FormControl(''),
        customerName: new FormControl('', Validators.required),
        customerEmail: new FormControl('', [Validators.required, Validators.email]),
        customerLocation: new FormControl('', Validators.required),
        customerBusinessLine: new FormControl('', Validators.required),
        contactPersonName: new FormControl('', Validators.required),
        contactPersonEmail: new FormControl('', [Validators.required, Validators.email]),
        productGroup: new FormControl('', Validators.required),
        productLine: new FormControl('', Validators.required),
        product: new FormControl('', Validators.required),
        demoSite: new FormControl('', Validators.required),
        demoDate: new FormControl('', Validators.required),
        demoSlot: new FormControl('', Validators.required),
        numberOfGuests: new FormControl(1, [Validators.required, Validators.min(1)]),
        demoExpectation: new FormControl('', [Validators.required, Validators.minLength(10), Validators.maxLength(100)]),
        aseDressCode: new FormControl(''),
        aseTechnicalKnowledgeRating: new FormControl(0),
        aseClinicalKnowledgeRating: new FormControl(0),
        demoSitePrepared: new FormControl(''),
        aseCustomerHandlingRating: new FormControl(0),
        siteAmbianceRating: new FormControl(0),
        productRating: new FormControl(0),
        customerConvinced: new FormControl(''),
        feedback: new FormControl(''),
        comment: new FormControl(''),
        status: new FormControl('Submitted'),
        authority: new FormControl('')
      }
    );
    document.querySelector('body').setAttribute('style', 'background-color: gainsboro;');
  }

  ngOnInit() {
    this.overlayMessage = 'Please Wait';
    this.saveInProgress = true;
    document.getElementById('overlay').style.display = 'flex';
    this.demoRequest.controls.requesterUserId.patchValue(this.currentUser.username);
    this.demoRequest.controls.requesterName.patchValue(this.currentUser.name);
    this.getProductList();
    this.getRequest();
  }
  getRequest() {
    this.dataService.getDemoRequestByUser(this.currentUser.username).subscribe(
      data => {
        this.previousRequests = data.filter(request => request.status === 'Approved' && this.commonMethods.calc(request.demoDate));
        if (this.previousRequests.length > 0) {
          this.toastr.warning('Please Fill the Feedback for Previous Demo', 'New Request cannot be created!!', {
            tapToDismiss: true,
            closeButton: true,
            timeOut: 5000,
            positionClass: 'toast-top-right'});
          this.router.navigate(['/demoRequests']).then(r => {
          });
        } else {
          this.saveInProgress = false;
          document.getElementById('overlay').style.display = 'none';
        }
      },
      () => {
        this.router.navigate(['/demoRequests']).then(r => {
        });
      }
    );
  }
  onlyUnique(value, index, self) {
    return self.indexOf(value) === index;
  }
  ascendingSort(a, b) {
    const item1 = a; // ignore upper and lowercase
    const item2 = b; // ignore upper and lowercase
    if (item1 < item2) {
      return -1;
    }
    if (item1 > item2) {
      return 1;
    }

    // names must be equal
    return 0;
  }
  getProductList() {
    this.dataService.getAllProducts().subscribe(
      data => {
        this.productList = data;
        this.productGroups = [];
        for (let i = 0; i < (this.productList.length); i++) {
          const productGroup = this.productList[i].productGroup;
          this.productGroups.push(productGroup);
        }
        this.productGroups = this.productGroups.filter(this.onlyUnique);
        this.productGroups = this.productGroups.sort(this.ascendingSort);
      }
    );
  }
  onSubmit() {
    if (!this.demoRequest.valid) {
      return;
    }
    this.overlayMessage = 'Generating Request Number';
    this.saveInProgress = true;
    document.getElementById('overlay').style.display = 'flex';
    this.demoRequest.controls.demoDate.patchValue(moment(this.demoRequest.controls.demoDate.value).format('DD-MM-YYYY'));
    this.demoRequest.controls.requestTimestamp.patchValue(moment().format('DD-MM-YYYY hh:mm:ss A'));
    this.dataService.createDemoRequest(this.demoRequest.value).subscribe(
      data => {
        this.toastr.success((data.requestNumber).toString(), 'Demo Request ID', {
          tapToDismiss: true,
          closeButton: true,
          timeOut: 5000,
          positionClass: 'toast-top-right'});
        this.mailService.sendMailForNewRequest(data.requestNumber).subscribe(() => {});
        this.demoRequest.reset();
        this.router.navigate(['/']).then(r => {});
        this.saveInProgress = false;
        document.getElementById('overlay').style.display = 'none';
      }, error => {
        this.error = error;
        this.saveInProgress = false;
        document.getElementById('overlay').style.display = 'none';
      }
    );
  }

  productGroupSelected() {
     this.productListFiltered = this.productList.filter(product => product.productGroup === this.demoRequest.controls.productGroup.value);
     this.productLines = [];
     this.products = [];
     for (let i = 0; i < (this.productListFiltered.length); i++) {
      const productLine = this.productListFiltered[i].productLine;
      this.productLines.push(productLine);
    }
     this.productLines = this.productLines.filter(this.onlyUnique);
     this.productLines = this.productLines.sort(this.ascendingSort);
     this.productLineSelected();
  }

  productLineSelected() {
    // tslint:disable-next-line:max-line-length
    this.productListFiltered = this.productList.filter(product => product.productGroup === this.demoRequest.controls.productGroup.value && product.productLine === this.demoRequest.controls.productLine.value);
    this.products = [];
    for (let i = 0; i < (this.productListFiltered.length); i++) {
      const product = this.productListFiltered[i].product;
      this.products.push(product);
    }
    this.products = this.products.filter(this.onlyUnique);
    this.products = this.products.sort(this.ascendingSort);
  }
}
