import { TestBed } from '@angular/core/testing';

import { CommonMethodsService } from './common-methods.service';

describe('CommonMethodsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CommonMethodsService = TestBed.get(CommonMethodsService);
    expect(service).toBeTruthy();
  });
});
