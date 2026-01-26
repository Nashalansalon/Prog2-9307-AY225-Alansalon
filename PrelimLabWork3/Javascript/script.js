function computeGrades() {
    const attendance = parseFloat(document.getElementById("attendance").value);
    const lab1 = parseFloat(document.getElementById("lab1").value);
    const lab2 = parseFloat(document.getElementById("lab2").value);
    const lab3 = parseFloat(document.getElementById("lab3").value);

    const labAverage = (lab1 + lab2 + lab3) / 3;
    const classStanding = (attendance * 0.40) + (labAverage * 0.60);

    const requiredPass = (75 - (classStanding * 0.30)) / 0.70;
    const requiredExcellent = (100 - (classStanding * 0.30)) / 0.70;

    let output = "";
    output += "Attendance: " + attendance + "\n";
    output += "Lab Work 1: " + lab1 + "\n";
    output += "Lab Work 2: " + lab2 + "\n";
    output += "Lab Work 3: " + lab3 + "\n\n";

    output += "Lab Work Average: " + labAverage.toFixed(2) + "\n";
    output += "Class Standing: " + classStanding.toFixed(2) + "\n\n";

    output += "Required Prelim Exam Grade:\n";
    output += "To Pass (75): " + requiredPass.toFixed(2) + "\n";
    output += "To Excel (100): " + requiredExcellent.toFixed(2) + "\n";

    if (requiredPass > 100) {
        output += "\nRemark: Passing is no longer possible.";
    } else {
        output += "\nRemark: You can still pass the Prelim period.";
    }

    document.getElementById("output").textContent = output;
}