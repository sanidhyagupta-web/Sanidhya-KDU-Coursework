const emp1 = {
    id: 1,
    name: "Alice Johnson",
    age: 30,
    salary: 85000,
    department: "Engineering",
    skills: ["JavaScript", "React", "Node.js"],
    experience: 5
}

const emp2 = {
    id: 2,
    name: "Sanidhya Gupta",
    age: 21,
    salary: 1000000000,
    department: "Engineering",
    skills: ["JavaScript", "React", "Node.js","Spring Boot","AWS"],
    experience: 0.5
}

const emp3 = {
    id: 3,
    name: "Devesh Piryani",
    age: 21,
    salary: 1000000000,
    department: "Engineering",
    skills: ["JavaScript", "React", "Node.js","Spring Boot","AWS"],
    experience: 2
}

const emp4 = {
    id: 4,
    name: "Isha Nanda",
    age: 21,
    salary: 1000,
    department: "Engineering",
    skills: ["JavaScript", "React", "Node.js","Spring Boot","AWS","MultiThreading","Kafka","Angular"],
    experience: 0.5
}

const emp5 = {
    id: 5,
    name: "Kritik",
    age: 21,
    salary: 100,
    department: "Commerce",
    skills: ["Communication", "Bussiness Games","Critical Thinking"],
    experience: 1
}
// ----------------Part 1 ---------------------- 
function getEmployee(employee){
    const {name,department,salary} = employee
    return `${name} works in ${department} and earns ${salary}`
}

function addSkill(employeeAddSkill){
    const {employee,skillsToBeAdded} = employeeAddSkill
    employee.skills = [...employee.skills,skillsToBeAdded]
    return employee
}

function getFullInfo(employee){
    const {id , name , age , salary , department , skills , experience} = employee
    return `Employee has  
            id : ${id} 
            name : ${name} 
            age : ${age}
            salary : ${salary}
            department : ${department} 
            skills : ${skills}
            experience : ${experience}`
}

function compareEmployees(employees){
    const {employee1 , employee2} = employees
    const len1 = employee1.skills.length
    const len2 = employee2.skills.length
    if (len1 > len2) {
        return "Employee 1 has more skills than Employee 2"
    } else if(len1 < len2){
        return "Employee 2 has more skills than Employee 1"
    }else{
        return "Both Employee has same skills"
    }

}
// ----------------------Part 2------------------
function displayAllEmployees(employeeList){
    for(const employee of employeeList){
        console.log(getFullInfo(employee))
    }
}

function filterByExperience(employeeList,minExperience){
    return employeeList.filter(emp => emp.experience >= minExperience);
}

function summariesMapNameSalary(employeeList){
    return employeeList.map(emp => `Name : ${emp.name} ::: Salary : ${emp.salary}`)
}

function avgSalary(employeeList){
    return employeeList.reduce((sum,{salary})=> sum+salary,0)/employeeList.length
}

function departmentWiseEmployeeCount(employeeList){
        return employeeList.reduce((count, { department }) => {
        count[department] = (count[department] || 0) + 1;
        return count;
    }, {});
}

function highestPaidEmployee(employeeList){
    return employeeList.reduce((max,employee) => employee.salary > max.salary ? employee : max)
}

function sortEmployeeByExperience(employeeList){
    return employeeList.sort((emp1,emp2) => emp2.experience - emp1.experience)
}

// --------------------Part3 -------------------------

function extractNameDepartmentSalary(employee){
    const {name,department,salary} = employee
    return `Name : ${name} :: Department : ${department} :: Salary : ${salary}`
}

function topAndBottomPaidEmployees(employeeList){
    if(employeeList.length == 0){
        return null;
    }

    if(employeeList.length == 1){
        return employeeList
    }

    const sortedEmployees = [...employeeList].sort(
        (a, b) => a.salary - b.salary
    );

    const [lowestPaid, ...rest] = sortedEmployees;
    const highestPaid = rest[rest.length - 1];

    return { highestPaid, lowestPaid };

}

function mergeSkills(employee){
    const {employee1,employee2} = employee
    return [...new Set([...employee1.skills,...employee2.skills])]  
}

function totalEmployeesAverageAge(...employees){
    const len = employees.length
    const avgAge = employees.reduce((sum,{age}) => sum+age , 0)/len
    return {len , avgAge}
}

function getAnalytics(employeeList) {
    return employeeList.reduce((skillMap, { skills }) => {
        skills.forEach(skill => {
            skillMap[skill] = (skillMap[skill] || 0) + 1;
        });
        return skillMap;
    }, {});
}

console.log(getEmployee(emp1))

console.log(addSkill({
    employee : emp5,
    skillsToBeAdded : "Brain Storming"
}))

console.log(getFullInfo(emp1))

console.log(compareEmployees({
    employee1 : emp2,
    employee2 : emp4
}))

const employeeList = [emp1,emp2,emp3,emp4,emp5]

displayAllEmployees(employeeList)

console.log(filterByExperience(employeeList,2))

console.log(summariesMapNameSalary(employeeList))

console.log(avgSalary(employeeList))

console.log(departmentWiseEmployeeCount(employeeList))

console.log(highestPaidEmployee(employeeList))

console.log(sortEmployeeByExperience(employeeList))

console.log(extractNameDepartmentSalary(emp1))

console.log(topAndBottomPaidEmployees(employeeList))

console.log(mergeSkills({
    employee1 : emp1,
    employee2 : emp2
}))

console.log(totalEmployeesAverageAge(emp1,emp2,emp3))

console.log(getAnalytics(employeeList))