import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {DataService} from '../_services/data.service';
import {Product} from '../_models/product-details';
import {ToastrService} from 'ngx-toastr';
import {ActivatedRoute, Router} from '@angular/router';
import {StarRatingComponent} from 'ng-starrating';
import {DemoRequest} from '../_models/demo-request';
import {error} from 'util';
import {AuthenticationService} from '../_services/authentication.service';
import {User} from '../_models/user';
import * as moment from 'moment';
import {CommonMethodsService} from '../_services/common-methods.service';

@Component({
  selector: 'app-demo-feedback',
  templateUrl: './demo-feedback.component.html',
  styleUrls: ['./demo-feedback.component.css']
})
export class DemoFeedbackComponent implements OnInit {
  public demoFeedback: FormGroup;
  public requestNumber: number;
  public demoRequest: DemoRequest;
  public saveInProgress: boolean;
  public error: string;
  public currentUser: User;
  public applicationSupportEngineerTab: boolean;

  constructor(private formBuilder: FormBuilder,
              private authenticationService: AuthenticationService,
              private dataService: DataService,
              private commonMethods: CommonMethodsService,
              private toastr: ToastrService,
              private router: Router,
              private avRoute: ActivatedRoute) {
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
    this.requestNumber = this.avRoute.snapshot.params.requestNumber;
    this.demoFeedback = this.formBuilder.group(
      {
        aseDressCode: new FormControl('', Validators.required),
        aseTechnicalKnowledgeRating: new FormControl(3, Validators.required),
        aseClinicalKnowledgeRating: new FormControl(3, Validators.required),
        demoSitePrepared: new FormControl('', Validators.required),
        aseCustomerHandlingRating: new FormControl(3, Validators.required),
        siteAmbianceRating: new FormControl(3, Validators.required),
        productRating: new FormControl(3, Validators.required),
        customerConvinced: new FormControl('', Validators.required),
        feedback: new FormControl('', [Validators.required, Validators.minLength(10), Validators.maxLength(100)]),
        comment: new FormControl('', [Validators.required, Validators.minLength(10), Validators.maxLength(100)]),
      }
    );
    document.querySelector('body').setAttribute('style', 'background-color: gainsboro;');
  }

  ngOnInit() {
    this.applicationSupportEngineerTab = true;
    this.getRequest();
  }

  toggle() {
    this.demoFeedback.markAsTouched();
    // tslint:disable-next-line:max-line-length
    if (this.demoFeedback.controls.aseDressCode.valid && this.demoFeedback.controls.aseTechnicalKnowledgeRating.valid && this.demoFeedback.controls.aseClinicalKnowledgeRating.valid && this.demoFeedback.controls.demoSitePrepared.valid && this.demoFeedback.controls.aseCustomerHandlingRating.valid) {
      this.applicationSupportEngineerTab = !this.applicationSupportEngineerTab;
      this.demoFeedback.markAsUntouched();
    } else {
      return;
    }
  }

  getRequest() {
    this.dataService.getDemoRequestById(this.requestNumber).subscribe(
      data => {
        this.demoRequest = data;
        this.validatePage();
      },
      () => {
        this.router.navigate(['/demoRequests']).then(r => {
        });
      }
    );
  }

  validatePage() {
    if (this.demoRequest.status !== 'Approved' || (this.demoRequest.requesterUserId.toString() !== this.currentUser.username && this.currentUser.role !== 'Admin')
      || !this.commonMethods.calc(this.demoRequest.demoDate)) {
      this.router.navigate(['/demoRequests']).then(r => {
      });
    }
  }
  onRateProduct($event: { oldValue: number, newValue: number, starRating: StarRatingComponent }) {
    this.demoFeedback.controls.productRating.patchValue($event.newValue);
  }
  onRateAseTechnicalKnowledge($event: { oldValue: number; newValue: number; starRating: StarRatingComponent }) {
    this.demoFeedback.controls.aseTechnicalKnowledgeRating.patchValue($event.newValue);
  }

  onRateAseClinicalKnowledge($event: { oldValue: number; newValue: number; starRating: StarRatingComponent }) {
    this.demoFeedback.controls.aseClinicalKnowledgeRating.patchValue($event.newValue);
  }

  onRateAseCustomerHandling($event: { oldValue: number; newValue: number; starRating: StarRatingComponent }) {
    this.demoFeedback.controls.aseCustomerHandlingRating.patchValue($event.newValue);
  }


  onRateSiteAmbiance($event: { oldValue: number; newValue: number; starRating: StarRatingComponent }) {
    this.demoFeedback.controls.siteAmbianceRating.patchValue($event.newValue);
  }

  onSubmit() {

    if (!this.demoFeedback.valid) {
      return;
    }
    this.saveInProgress = true;
    document.getElementById('overlay').style.display = 'flex';
    this.demoRequest.status = 'Completed';
    this.demoRequest.aseDressCode = this.demoFeedback.controls.aseDressCode.value;
    this.demoRequest.aseTechnicalKnowledgeRating = this.demoFeedback.controls.aseTechnicalKnowledgeRating.value;
    this.demoRequest.aseClinicalKnowledgeRating = this.demoFeedback.controls.aseClinicalKnowledgeRating.value;
    this.demoRequest.demoSitePrepared = this.demoFeedback.controls.demoSitePrepared.value;
    this.demoRequest.aseCustomerHandlingRating = this.demoFeedback.controls.aseCustomerHandlingRating.value;
    this.demoRequest.siteAmbianceRating = this.demoFeedback.controls.siteAmbianceRating.value;
    this.demoRequest.productRating = this.demoFeedback.controls.productRating.value;
    this.demoRequest.customerConvinced = this.demoFeedback.controls.customerConvinced.value;
    this.demoRequest.feedback = this.demoFeedback.controls.feedback.value;
    this.demoRequest.comment = this.demoFeedback.controls.comment.value;
    this.dataService.updateDemoRequests(this.demoRequest).subscribe(
      () => {
        this.demoFeedback.reset();
        this.router.navigate(['/demoRequests']).then(r => {
        });
        // tslint:disable-next-line:no-shadowed-variable
      }, error => {
        this.error = error;
      }
    );
  }
}
