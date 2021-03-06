import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMilestone } from 'app/shared/model/milestone.model';
import { AccountService } from 'app/core';
import { MilestoneService } from './milestone.service';

@Component({
  selector: 'jhi-milestone',
  templateUrl: './milestone.component.html'
})
export class MilestoneComponent implements OnInit, OnDestroy {
  milestones: IMilestone[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected milestoneService: MilestoneService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.milestoneService
      .query()
      .pipe(
        filter((res: HttpResponse<IMilestone[]>) => res.ok),
        map((res: HttpResponse<IMilestone[]>) => res.body)
      )
      .subscribe(
        (res: IMilestone[]) => {
          this.milestones = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInMilestones();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMilestone) {
    return item.id;
  }

  registerChangeInMilestones() {
    this.eventSubscriber = this.eventManager.subscribe('milestoneListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
