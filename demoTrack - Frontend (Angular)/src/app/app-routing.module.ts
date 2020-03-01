import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {DemoRequestFormComponent} from './demo-request-form/demo-request-form.component';
import {DemoRequestsComponent} from './demo-requests/demo-requests.component';
import {AuthGuard} from './_guard/auth.guard';
import {DemoFeedbackComponent} from './demo-feedback/demo-feedback.component';
import {DemoHistoryComponent} from './demo-history/demo-history.component';
import {AddUserComponent} from './add-user/add-user.component';
import {AddProductComponent} from './add-product/add-product.component';
import {Role} from './_models/role.enum';


const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'demoForm', component: DemoRequestFormComponent, canActivate: [AuthGuard], data: {roles: [Role.Sales, Role.Admin]}},
  {path: 'demoRequests', component: DemoRequestsComponent, canActivate: [AuthGuard]},
  {path: '', component: DemoRequestsComponent, canActivate: [AuthGuard]},
  {path: 'feedback/:requestNumber', component: DemoFeedbackComponent, canActivate: [AuthGuard], data: {roles: [Role.Sales, Role.Admin]}},
  {path: 'demoHistory', component: DemoHistoryComponent, canActivate: [AuthGuard]},
  {path: 'addUser', component: AddUserComponent, canActivate: [AuthGuard], data: {roles: [Role.Admin]}},
  {path: 'addProduct', component: AddProductComponent, canActivate: [AuthGuard], data: {roles: [Role.Admin]}},
  { path: '**', redirectTo: '/' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
