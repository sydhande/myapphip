import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOwner } from 'app/shared/model/owner.model';

@Component({
  selector: 'jhi-owner-detail',
  templateUrl: './owner-detail.component.html'
})
export class OwnerDetailComponent implements OnInit {
  owner: IOwner;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ owner }) => {
      this.owner = owner;
    });
  }

  previousState() {
    window.history.back();
  }
}
