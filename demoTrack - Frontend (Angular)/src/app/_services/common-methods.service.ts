import { Injectable } from '@angular/core';
import * as moment from 'moment';

@Injectable({
  providedIn: 'root'
})
export class CommonMethodsService {

  constructor() { }

  calc(date: any) {
    const today = moment().format('DD-MM-YYYY');
    const now = moment(today, 'DD-MM-YYYY');
    const format = moment(date, 'DD-MM-YYYY');
    if (now.diff(format, 'days') >= 0) {
      // if demo date is in past or is today
      return true;
    } else {
      return false;
    }
  }
}
