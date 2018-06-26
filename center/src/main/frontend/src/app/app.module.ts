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

const appRoutes: Routes = [
<<<<<<< HEAD
    { path: '', component: HomePageComponent },
    { path: 'center', component: HomePageComponent },
    { path: 'app-logs', component: AppLogsComponent },
    { path: 'server-logs', component: ServerLogsComponent },
    { path: 'OS-logs', component: OSLogsComponent },
    { path: 'firewall-logs', component: FirewallLogsComponent }
=======
    { path: '', component: AppComponent},
    { path: 'center', component: HomePageComponent },
    { path: 'alarms', component: AlarmsComponent}
>>>>>>> refs/remotes/origin/alarms
];

@NgModule({
    declarations: [
        AppComponent,
<<<<<<< HEAD
        HomePageComponent,
        AppLogsComponent,
        OSLogsComponent,
        ServerLogsComponent,
        FirewallLogsComponent
=======
       HomePageComponent,
       AppLogsComponent,
       OSLogsComponent,
       ServerLogsComponent,
       FirewallLogsComponent,
       AlarmsComponent
>>>>>>> refs/remotes/origin/alarms
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
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule { }
