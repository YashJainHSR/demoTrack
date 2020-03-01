import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import {ReactiveFormsModule, FormsModule} from '@angular/forms';
import {
    MatButtonModule,
    MatSelectModule,
    MatCheckboxModule,
    MatInputModule,
    MatCardModule,
    MatIconModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MAT_DATE_LOCALE,
    MatRadioModule,
    MAT_RADIO_DEFAULT_OPTIONS,
    MatExpansionModule, MatDialogModule, MatSidenavModule
} from '@angular/material';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavbarComponent } from './navbar/navbar.component';
import { DemoRequestFormComponent } from './demo-request-form/demo-request-form.component';
import { DemoRequestsComponent } from './demo-requests/demo-requests.component';
import {JwtInterceptor} from './_helpers/jwt.interceptor';
import {ErrorInterceptor} from './_helpers/error.interceptor';
import { ToastrModule } from 'ngx-toastr';
import { RatingModule } from 'ng-starrating';
import { DemoFeedbackComponent } from './demo-feedback/demo-feedback.component';
import { DemoHistoryComponent } from './demo-history/demo-history.component';
import { AddUserComponent} from './add-user/add-user.component';
import { AddProductComponent } from './add-product/add-product.component';
import {LocationStrategy, HashLocationStrategy} from '@angular/common';
import { RequestDetailsDialogComponent } from './request-details-dialog/request-details-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavbarComponent,
    DemoRequestFormComponent,
    DemoRequestsComponent,
    DemoFeedbackComponent,
    DemoHistoryComponent,
    AddUserComponent,
    AddProductComponent,
    RequestDetailsDialogComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
        MatButtonModule, MatSelectModule, MatCheckboxModule, MatInputModule, MatCardModule, MatIconModule,
        FormsModule, MatDatepickerModule, MatNativeDateModule, MatTableModule, MatPaginatorModule, MatSortModule,
        HttpClientModule, CommonModule,
        ToastrModule.forRoot(), MatRadioModule,
        RatingModule, MatExpansionModule, MatDialogModule, MatSidenavModule
    ],
  providers: [
    {provide: MAT_DATE_LOCALE, useValue: 'en-IN'},
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    {provide: LocationStrategy, useClass: HashLocationStrategy},
    MatNativeDateModule],
  bootstrap: [AppComponent],
  entryComponents: [RequestDetailsDialogComponent]
})
export class AppModule { }
