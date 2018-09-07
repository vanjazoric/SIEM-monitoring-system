import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {LoginComponent} from './components/login/login.component';

const routes: Routes = [
    { 
        path:'login',
        component: LoginComponent
    }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(routes);