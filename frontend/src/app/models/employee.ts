import { Company } from "./company";
import { Device } from "./device";

export interface Employee {
    id: string,
    name: string,
    email: string,
    companyId: string,
    devicesId: string[]
}
