import { TestBed, inject } from '@angular/core/testing';

import { RecursiveControllerService } from './recursive-controller.service';

describe('RecursiveControllerService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RecursiveControllerService]
    });
  });

  it('should be created', inject([RecursiveControllerService], (service: RecursiveControllerService) => {
    expect(service).toBeTruthy();
  }));
});
