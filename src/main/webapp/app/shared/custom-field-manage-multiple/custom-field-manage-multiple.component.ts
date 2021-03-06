import { Component, Input, OnInit } from '@angular/core';
import { Field, IField } from 'app/shared/model/field.model';
import { CustomFieldManageMultipleService } from 'app/shared/custom-field-manage-multiple/custom-field-manage-multiple.service';

@Component({
  selector: 'jhi-custom-field-manage-multiple',
  templateUrl: './custom-field-manage-multiple.component.html',
  styles: []
})
export class CustomFieldManageMultipleComponent implements OnInit {
  @Input() existingFields: IField[] = [];
  newField: boolean;
  field: IField = new Field();
  selectedField: IField = new Field();
  cols: any[];
  displayDialog: boolean;
  displayColumnDialog: boolean;
  newColumnName: string;

  constructor(private service: CustomFieldManageMultipleService) {}

  ngOnInit() {
    this.cols = [];
  }

  showDialogToAdd() {
    this.newField = true;
    this.field = new Field();
    this.displayDialog = true;
  }

  onClickAddColumnButton() {
    this.displayColumnDialog = true;
  }

  save() {
    if (this.newField) {
      this.existingFields.push(this.field);
    } else {
      this.existingFields[this.existingFields.indexOf(this.selectedField)] = this.field;
    }
    this.field = null;
    this.displayDialog = false;
  }

  delete() {
    const index = this.existingFields.indexOf(this.selectedField);
    this.existingFields = this.existingFields.filter((val, i) => i != index);
    this.field = null;
    this.displayDialog = false;
  }

  onRowSelect(event) {
    this.newField = false;
    this.field = this.cloneCar(event.data);
    this.displayDialog = true;
  }

  cloneCar(c: IField): IField {
    const field = {};
    for (let prop in c) {
      field[prop] = c[prop];
    }
    return field;
  }

  saveNewColumn() {
    this.cols.push({
      field: this.newColumnName.toLocaleLowerCase(),
      header: this.newColumnName
    });
    this.displayColumnDialog = false;
  }

  async onUpload(event: any) {
    console.log(event);
    const resp = (await this.service.uploadFile(event.files[0])) as Object[];
    this.createColumnsFromExcelData(resp.shift());
    this.existingFields.push(...resp);
  }

  private createColumnsFromExcelData(item: any) {
    for (let key in item) {
      if (item.hasOwnProperty(key)) {
        this.cols.push({
          field: key,
          header: key
        });
      }
    }
  }
}
