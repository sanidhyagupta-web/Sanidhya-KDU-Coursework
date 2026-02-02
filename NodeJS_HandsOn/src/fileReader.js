import * as fs from 'fs';
import * as path from 'path';

function readEmployeeData(filePath){
    const absolutePath = path.resolve(filePath);
    const fileData = fs.readFileSync(absolutePath, 'utf-8');
    const employeeData = JSON.parse(fileData);
    return employeeData;
}

export { readEmployeeData}