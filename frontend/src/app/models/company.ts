import { Employee } from "./employee";

export interface Company {
    id: string,
    name: string,
    employees: Employee[]
}