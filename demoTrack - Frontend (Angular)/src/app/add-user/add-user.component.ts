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
import {PasswordEncryptionService} from '../_services/password-encryption.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {
  public addUser: FormGroup;
  public saveInProgress: boolean;
  public error: string;

  constructor(private formBuilder: FormBuilder,
              private dataService: DataService,
              private passwordEncryptionService: PasswordEncryptionService,
  private toastr: ToastrService,
              private router: Router) {
    this.addUser = this.formBuilder.group(
      {
        username: new FormControl('', Validators.required),
        password: new FormControl('Pwd@2020'),
        name: new FormControl('', Validators.required),
        email: new FormControl('', [Validators.required, Validators.email]),
        role: new FormControl('', Validators.required)
      }
    );
    document.querySelector('body').setAttribute('style', 'background-color: gainsboro;');
  }

  ngOnInit() {
  }

  onSubmit() {

    if (!this.addUser.valid) {
      return;
    }
    this.saveInProgress = true;
    document.getElementById('overlay').style.display = 'flex';
    this.addUser.controls.password.patchValue(this.passwordEncryptionService.encryptPasswordWithAES(this.addUser.controls.password.value));
    this.dataService.createNewUser(this.addUser.value).subscribe(
      () => {
        this.addUser.reset();
        this.router.navigate(['/']).then(r => {
        });
        // tslint:disable-next-line:no-shadowed-variable
      }, error => {
        this.error = error;
        this.saveInProgress = false;
        document.getElementById('overlay').style.display = 'none';
      }
    );
  }
}
