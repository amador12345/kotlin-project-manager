import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PmAppSharedModule } from 'app/shared';
import {
  ProjectComponent,
  ProjectDetailComponent,
  ProjectUpdateComponent,
  ProjectDeletePopupComponent,
  ProjectDeleteDialogComponent,
  projectRoute,
  projectPopupRoute
} from './';
import { ProjectManageComponent } from 'app/entities/project/manage/manage.component';
import { TableModule } from 'primeng/table';
import { PanelModule } from 'primeng/panel';
import { AutoCompleteModule, CalendarModule, EditorModule, InputTextModule } from 'primeng/primeng';

const ENTITY_STATES = [...projectRoute, ...projectPopupRoute];

@NgModule({
  imports: [
    PmAppSharedModule,
    RouterModule.forChild(ENTITY_STATES),
    InputTextModule,
    CalendarModule,
    EditorModule,
    AutoCompleteModule,
    PanelModule,
    TableModule
  ],
  declarations: [
    ProjectComponent,
    ProjectDetailComponent,
    ProjectUpdateComponent,
    ProjectDeleteDialogComponent,
    ProjectDeletePopupComponent,
    ProjectManageComponent
  ],
  entryComponents: [ProjectComponent, ProjectUpdateComponent, ProjectDeleteDialogComponent, ProjectDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PmAppProjectModule {}
