import { TestBed } from '@angular/core/testing';

import { PasswordEncryptionService } from './password-encryption.service';

describe('PasswordEncryptionService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PasswordEncryptionService = TestBed.get(PasswordEncryptionService);
    expect(service).toBeTruthy();
  });
});
