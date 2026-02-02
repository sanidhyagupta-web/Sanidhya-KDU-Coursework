import * as fs from 'fs';
import * as path from 'path';

function writeReport(filePath,content){
    const directory = path.dirname(filePath);

    if(!fs.existsSync(directory)){
        fs.mkdirSync(directory, { recursive: true})
    }

    fs.writeFileSync(filePath, content, 'utf-8');
}

export { writeReport }