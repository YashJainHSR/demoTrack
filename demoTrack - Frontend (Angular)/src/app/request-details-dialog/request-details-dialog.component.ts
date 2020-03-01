import {Component, Inject, OnInit} from '@angular/core';
import {User} from '../_models/user';
import {AuthenticationService} from '../_services/authentication.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {CommonMethodsService} from '../_services/common-methods.service';
import {DemoRequest} from '../_models/demo-request';

@Component({
  selector: 'app-request-details-dialog',
  templateUrl: './request-details-dialog.component.html',
  styleUrls: ['./request-details-dialog.component.css']
})
export class RequestDetailsDialogComponent implements OnInit {

  public currentUser: User;
  constructor(
    private authenticationService: AuthenticationService,
    public dialogRef: MatDialogRef<RequestDetailsDialogComponent>,
    private commonMethods: CommonMethodsService,
    @Inject(MAT_DIALOG_DATA) public data: DemoRequest) {
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
  }

  onNoClick(): void {
    this.dialogRef.close('back');
  }
  calc(demoDate: any) {
    return this.commonMethods.calc(demoDate);
  }
  ngOnInit() {
  }

}
