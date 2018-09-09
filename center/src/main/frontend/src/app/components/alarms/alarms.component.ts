import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AlarmService } from '../../services/alarm.service';
import { Alarm } from '../../model/alarm';
declare var $ :any;

@Component({
  selector: 'app-alarms',
  templateUrl: './alarms.component.html',
  styleUrls: ['./alarms.component.css'],
  providers: [ AlarmService ]
})
export class AlarmsComponent implements OnInit {
  rForm : FormGroup;
  updateForm: FormGroup;
  selectedType: string;
  selectedTypeUpdate: string;
  post : any;
  description : string = '';
  duration : string = '';
  numOfItems : string = '';
  param : string = '';
  value : string = '';
  message : string = 'Polje je obavezno!';
  alarms : Alarm[];
  alarm : Alarm;
  alarmForUpdate: Alarm;

  constructor(private fb:FormBuilder, private alarmService : AlarmService) { 
    this.rForm = fb.group({
      'description' : [null, Validators.required],
      'duration' : [null, Validators.required],
      'numOfItems' : [null, Validators.required],
      'param' : [null, Validators.required],
      'value' : [null, Validators.required],
      'type' : [null, Validators.required]
    });
    this.updateForm = fb.group({
      'description' : [null, Validators.required],
      'duration' : [null, Validators.required],
      'numOfItems' : [null, Validators.required],
      'param' : [null, Validators.required],
      'value' : [null, Validators.required],
      'type' : [null, Validators.required]
    });
  }

  ngOnInit() {
    this.alarmService.getAlarms().subscribe( data => {
        this.alarms = data;
        for(let alarm of this.alarms){
          if(alarm.conditions=="null;null"){
            alarm.conditionsForShow = "------------------";
          }else{
            alarm.conditionsForShow = alarm.conditions.split(";")[0] + ": " + alarm.conditions.split(";")[1];
          }
        }
      });
  }

  selectedValue(){
    this.selectedType = this.rForm.controls['type'].value;
  }

  selectedValueUpdate(){
    this.selectedTypeUpdate = this.updateForm.controls['type'].value;
  }

  updateAlarmValue(alarm: Alarm){
    this.alarmForUpdate = alarm;
    this.updateForm.controls['description'].setValue(alarm.description);
    this.updateForm.controls['duration'].setValue(alarm.duration);
    this.updateForm.controls['numOfItems'].setValue(alarm.numOfItems);
    this.updateForm.controls['param'].setValue(alarm.conditions.split(";")[0]);
    this.updateForm.controls['value'].setValue(alarm.conditions.split(";")[1]);
    this.updateForm.controls['type'].setValue(alarm.type);
    if(alarm.type == "loginUsername"){
      this.selectedTypeUpdate = "loginUsername";
    }
    else if(alarm.type == "loginMachine"){
      this.selectedTypeUpdate = "loginMachine";
    }
    else{
      this.selectedTypeUpdate = "nesto";
    }
  }

  updateAlarm(data: any){
    this.alarmForUpdate.description = data.description;
    this.alarmForUpdate.duration = data.duration;
    this.alarmForUpdate.numOfItems = data.numOfItems;
    this.alarmForUpdate.conditions = data.param + ";" + data.value;
    this.alarmForUpdate.type = data.type;
    if(data.type == "loginUsername" || data.type == "loginMachine"){
      this.alarmForUpdate.conditionsForShow = "------------------";
    }else{
      this.alarmForUpdate.conditionsForShow = data.param + ": " + data.value;
    }
    this.alarmService.updateAlarm(this.alarmForUpdate).subscribe(res => {console.log(res);});
    $("#updateAlarm").modal("toggle");
  }

  deleteAlarm(alarm: Alarm){
    this.alarmService.deleteAlarm(alarm.id).subscribe(
      res => {
        console.log(res);
        for(let i in this.alarms){
          if(this.alarms[i].id == alarm.id){
            this.alarms.splice(Number(i), 1);
          }
        }
      }
    );
  }

  addPost(post){
    this.description = post.description;
    this.duration = post.duration;
    this.numOfItems = post.numOfItems;
    this.param = post.param;
    this.value = post.value;
    let newAlarm = new Alarm(this.description, Number(this.numOfItems), Number(this.duration), this.param + ';' +this.value);
    newAlarm.type = post.type;
    this.alarmService.createAlarm(newAlarm)
      .subscribe(data =>{
        this.alarm = data;
        this.alarm.conditionsForShow = data.conditions.split(";")[0] + ": " + data.conditions.split(";")[1];
        this.alarms.push(this.alarm);
        $('#createAlarm').modal('hide');
      }
    );
  }

}
