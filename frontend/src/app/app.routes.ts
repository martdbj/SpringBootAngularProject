import { Routes } from '@angular/router';
import { EmployeeManagement } from './employee-management/employee-management';
import { CompanyManagement } from './company-management/company-management';
import { DeviceManagement } from './device-management/device-management';

export const routes: Routes = [
    {
        path: 'companies',
        component: CompanyManagement
    },
    {
        path: 'employees',
        component: EmployeeManagement
    },
    {
        path: 'devices',
        component: DeviceManagement
    }
];
