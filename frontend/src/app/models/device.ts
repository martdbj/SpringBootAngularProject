import { Employee } from "./employee";

export interface Device {
    serialNumber: string,
    name: string,
    type: number,
    employeeId: string
}
