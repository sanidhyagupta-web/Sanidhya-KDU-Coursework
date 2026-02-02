import { writeReport }  from "./reportGenerator.js";

function generateSummary(employees,outputPath){
    const totalEmployees = employees.length;
    const totalSalary = employees.reduce((sum, emp) => sum + emp.salary, 0);
    const averageSalary = (totalEmployees === 0) ? 0 : (totalSalary / totalEmployees).toFixed(2);

    const departmentcount = {};
    employees.forEach(emp => {
        const dept = emp.department;
        if(!departmentcount[dept]){
            departmentcount[dept] = {
                count : 0,
                totalSalary : 0,
                averageSalary : 0
            }
        }
        departmentcount[dept].count++;
        departmentcount[dept].totalSalary += emp.salary;
        departmentcount[dept].averageSalary = (departmentcount[dept].totalSalary / departmentcount[dept].count).toFixed(2);
        
    })
    const summary = {
        totalEmployees,
        totalSalary,
        averageSalary,
        departmentBreakdown: departmentcount
    }
    writeReport(outputPath, JSON.stringify(summary, null, 2));
}

function generateDepartmentReport(employees, department, outputPath){
    const departmentName = department;
    
    const departmentEmployees = employees.filter(emp => emp.department === departmentName)
    const employeeCount = departmentEmployees.length;

    let totalSalary = 0;
    const employeeList = []

    departmentEmployees.forEach(emp => {
        totalSalary += emp.salary;
        employeeList.push({
            name : emp.name,
            salary : emp.salary
        })
    })

    const averageSalary = employeeCount > 0 ? (totalSalary / employeeCount).toFixed(2) : 0;

    const departmentReport = {
        department,
        totalEmployees: employeeCount,
        employees: employeeList,
        totalSalary,
        averageSalary
    };

    writeReport(outputPath, JSON.stringify(departmentReport, null, 2));
}

function generateTopEarnersReport(employees, count, outputPath){
    const sortedEmployees = employees.sort((a,b) => b.salary - a.salary);
    var rank = 1;   
    const topEarners = sortedEmployees.slice(0, count).map(emp => ({
        rank: rank++,
        name: emp.name,
        department: emp.department,
        salary: emp.salary
    }));

    writeReport(outputPath, JSON.stringify(topEarners, null, 2));
}

export { generateSummary }
export { generateDepartmentReport }
export { generateTopEarnersReport }