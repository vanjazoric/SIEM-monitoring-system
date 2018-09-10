import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule, ReactiveFormsModule }   from '@angular/forms';
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { AppLogsComponent } from './components/app-logs/app-logs.component';
import { OSLogsComponent } from './components/os-logs/os-logs.component';
import { ServerLogsComponent } from './components/server-logs/server-logs.component';
import { FirewallLogsComponent } from './components/firewall-logs/firewall-logs.component';
import { AlarmsComponent } from './components/alarms/alarms.component';
import { LogInComponent } from './components/log-in/log-in.component';

import { AlarmService } from './services/alarm.service';
import { CenterService } from './services/center.service';
import { WebsocketService } from './services/websocket.service';

import { CreateReportComponent } from './components/create-report/create-report.component';
import { ReportsComponent } from './components/reports/reports.component';
import { AgentsComponent } from './components/agents/agents.component';


const appRoutes: Routes = [
    { path: 'alarms', component: AlarmsComponent},
    { path: '', component: HomePageComponent },
    { path: 'app-logs', component: AppLogsComponent },
    { path: 'server-logs', component: ServerLogsComponent },
    { path: 'OS-logs', component: OSLogsComponent },
    { path: 'firewall-logs', component: FirewallLogsComponent },
    { path: 'login', component: LogInComponent },
    { path: 'createReport', component: CreateReportComponent },
    { path: 'reports', component: ReportsComponent },
    { path: 'agents', component: AgentsComponent }
];

@NgModule({
    declarations: [
        AppComponent,
       HomePageComponent,
       AppLogsComponent,
       OSLogsComponent,
       ServerLogsComponent,
       FirewallLogsComponent,
       AlarmsComponent,
       LogInComponent,
       CreateReportComponent,
       ReportsComponent,
       AgentsComponent
    ],
    imports: [
        BrowserModule,
        HttpModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        RouterModule.forRoot(
            appRoutes,
            { enableTracing: true })
    ],
    providers: [
        AlarmService,
        CenterService,
        WebsocketService
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
