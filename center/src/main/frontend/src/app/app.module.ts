import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule }   from '@angular/forms';
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { AppLogsComponent } from './components/app-logs/app-logs.component';
import { OSLogsComponent } from './components/os-logs/os-logs.component';
import { ServerLogsComponent } from './components/server-logs/server-logs.component';
import { FirewallLogsComponent } from './components/firewall-logs/firewall-logs.component';

const appRoutes: Routes = [
    { path: '', component: HomePageComponent },
    { path: 'center', component: HomePageComponent },
    { path: 'app-logs', component: AppLogsComponent },
    { path: 'server-logs', component: ServerLogsComponent },
    { path: 'OS-logs', component: OSLogsComponent },
    { path: 'firewall-logs', component: FirewallLogsComponent }
];

@NgModule({
    declarations: [
        AppComponent,
        HomePageComponent,
        AppLogsComponent,
        OSLogsComponent,
        ServerLogsComponent,
        FirewallLogsComponent
    ],
    imports: [
        BrowserModule,
        HttpModule,
        HttpClientModule,
        FormsModule,
        RouterModule.forRoot(
            appRoutes,
            { enableTracing: true })
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule { }
