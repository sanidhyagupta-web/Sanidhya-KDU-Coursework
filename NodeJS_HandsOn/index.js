import { readEmployeeData } from "./src/fileReader.js";
import { generateSummary ,generateDepartmentReport , generateTopEarnersReport} from "./src/summaryReport.js";

const employeeData = readEmployeeData('./data/employees.json');

const args = process.argv.slice(2);
const command = args[0];

switch (command) {
  case "summary":
    generateSummary(employeeData, "./reports/summaryReport.json");
    break;

  case "department":
    if (!args[1]) {
      console.error("Please provide department name");
      process.exit(1);
    }
    generateDepartmentReport(
      employeeData,
      args[1],
      `./reports/${args[1].toLowerCase()}Report.json`
    );
    break;

  case "top":
    const n = args[1] ? Number(args[1]) : 5;
    generateTopEarnersReport(
      employeeData,
      n,
      "./reports/topEarnersReport.json"
    );
    break;

  default:
    generateSummary(employeeData, "./reports/summaryReport.json");
    generateDepartmentReport(
      employeeData,
      "Engineering",
      "./reports/engineeringReport.json"
    );
    generateTopEarnersReport(
      employeeData,
      5,
      "./reports/topEarnersReport.json"
    );
}
