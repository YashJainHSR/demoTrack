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

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {
  public addProduct: FormGroup;
  public saveInProgress: boolean;
  public error: string;

  constructor(private formBuilder: FormBuilder,
              private dataService: DataService,
              private toastr: ToastrService,
              private router: Router) {
    this.addProduct = this.formBuilder.group(
      {
        productGroup: new FormControl('', Validators.required),
        productLine: new FormControl('', Validators.required),
        product: new FormControl('', Validators.required)
      }
    );
    document.querySelector('body').setAttribute('style', 'background-color: gainsboro;');
  }

  ngOnInit() {
  }

  onSubmit() {

    if (!this.addProduct.valid) {
      return;
    }
    this.saveInProgress = true;
    document.getElementById('overlay').style.display = 'flex';
    this.dataService.createNewProduct(this.addProduct.value).subscribe(
      () => {
        this.addProduct.reset();
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
