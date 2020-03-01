import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MailService {

  constructor(private http: HttpClient) { }

  sendMailForNewRequest(requestNumber) {
    return this.http.post<string>(`${environment.apiUrl}/mail/newRequest`, requestNumber);
  }

  sendMailForApproval(request) {
    return this.http.post<string>(`${environment.apiUrl}/mail/approval`, request);
  }

}
