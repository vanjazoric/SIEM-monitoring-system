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
  post : any;
  description : string = '';
  duration : string = '';
  numOfItems : string = '';
  param : string = '';
  value : string = '';
  message : string = 'Polje je obavezno!';
  alarms : Alarm[];
  alarm : Alarm;

  constructor(private fb:FormBuilder, private alarmService : AlarmService) { 
    this.rForm = fb.group({
      'description' : [null, Validators.required],
      'duration' : [null, Validators.required],
      'numOfItems' : [null, Validators.required],
      'param' : [null, Validators.required],
      'value' : [null, Validators.required]
    })
  }

  ngOnInit() {
    this.alarmService.getAlarms()
      .subscribe( data => this.alarms = data);
  }

  addPost(post){
    this.description = post.description;
    this.duration = post.duration;
    this.numOfItems = post.numOfItems;
    this.param = post.param;
    this.value = post.value;
    let newAlarm = new Alarm(this.description, Number(this.numOfItems), Number(this.duration), this.param + ';' +this.value);
    this.alarmService.createAlarm(newAlarm)
      .subscribe(data =>{
        this.alarm = data;
        this.alarms.push(this.alarm);
        $('#exampleModal').modal('hide');
      }
    );
  }

}
