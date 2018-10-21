import { TestBed } from '@angular/core/testing';

import { Serve1Service } from './serve1.service';

describe('Serve1Service', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: Serve1Service = TestBed.get(Serve1Service);
    expect(service).toBeTruthy();
  });
});
