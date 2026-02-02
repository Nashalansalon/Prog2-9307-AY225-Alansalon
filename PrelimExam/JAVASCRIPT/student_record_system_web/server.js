const express = require('express');
const cors = require('cors');
const fs = require('fs');
const path = require('path');

const app = express();
app.use(cors());
// Serve the web UI from this folder
app.use(express.static(path.join(__dirname)));
// Accept text/csv and other text bodies for saving
app.use(express.text({ type: ['text/csv', 'text/*'], limit: '10mb' }));

// Absolute CSV path (your requested file)
const csvPath = 'C:\\Users\\USER\\OneDrive\\Documents\\2nd-sem-alansalon\\Prog2-9307-AY225-Alansalon\\PrelimExam\\JAVA\\class_records.csv';

app.get('/api/class_records', (req, res) => {
  fs.readFile(csvPath, 'utf8', (err, data) => {
    if (err) {
      return res.status(404).send('CSV not found on server: ' + csvPath);
    }
    res.set('Content-Type', 'text/csv');
    res.send(data);
  });
});

app.post('/api/class_records', (req, res) => {
  const csv = req.body || '';
  if (!csv) return res.status(400).send('No CSV provided');
  fs.writeFile(csvPath, csv, 'utf8', (err) => {
    if (err) return res.status(500).send('Write failed: ' + err.message);
    res.send('OK');
  });
});

const port = process.env.PORT || 3000;
app.listen(port, () => console.log(`Server running on http://localhost:${port}`));
