import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthenticationService} from '../_services/authentication.service';
import {ActivatedRoute, Router} from '@angular/router';
import {first} from 'rxjs/operators';
import {PasswordEncryptionService} from '../_services/password-encryption.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  imgURL = '/assets/logo.png';
  loginForm: FormGroup;
  loading = false;
  returnUrl: string;
  error: string;
  public loginProgress: boolean;
  constructor(private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private authenticationService: AuthenticationService,
              private passwordEncryptionService: PasswordEncryptionService
) {
    if (this.authenticationService.currentUserValue) {
      this.router.navigate(['/']).then(r => {});
    }
    this.loginForm = this.formBuilder.group({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });
  }

  ngOnInit() {
    this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';
  }

  show() {
    const el = document.querySelector('#pwd') as HTMLElement;
    if (el.getAttribute('type') === 'password') {
      el.setAttribute('type', 'text');
    } else {
      el.setAttribute('type', 'password');
    }
  }

  onSubmit() {
    if (this.loginForm.invalid) {
      return;
    }
    this.loginProgress = true;
    document.getElementById('overlay').style.display = 'flex';
    this.authenticationService.login((this.loginForm.get('username').value), this.passwordEncryptionService.encryptPasswordWithAES(this.loginForm.get('password').value))
      .pipe(first())
      .subscribe(
        data => {
          this.router.navigate([this.returnUrl]);
        },
        error => {
          this.loginProgress = false;
          document.getElementById('overlay').style.display = 'none';
          this.error = error;
          this.loading = false;
        });
  }
}
