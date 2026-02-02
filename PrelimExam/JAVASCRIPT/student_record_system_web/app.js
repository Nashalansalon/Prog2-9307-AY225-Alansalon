const csvFileInput = document.getElementById('csvFile');
const btnFetchServer = document.getElementById('btnFetchServer');
const btnSaveServer = document.getElementById('btnSaveServer');
const btnExport = document.getElementById('btnExport');
const status = document.getElementById('status');
const table = document.getElementById('recordsTable');
const tbody = table.querySelector('tbody');
const thead = table.querySelector('thead');
const btnAdd = document.getElementById('btnAdd');
const btnDelete = document.getElementById('btnDeleteTop');
const txtStudentID = document.getElementById('txtStudentID');
const txtFirstName = document.getElementById('txtFirstName');
const txtLastName = document.getElementById('txtLastName');

let headers = [];
let data = []; // array of objects
let selectedRowIndex = -1;

function setStatus(msg, ok=true){
  status.textContent = msg;
  status.style.color = ok ? 'white' : 'yellow';
}

function renderTable(){
  // headers
  thead.innerHTML = '';
  const tr = document.createElement('tr');
  headers.forEach(h=>{
    const th = document.createElement('th');
    th.textContent = h;
    tr.appendChild(th);
  });
  thead.appendChild(tr);

  // rows
  tbody.innerHTML = '';
  data.forEach((row, i)=>{
    const tr = document.createElement('tr');
    tr.dataset.index = i;
    tr.addEventListener('click', ()=>{
      selectRow(i);
    });
    headers.forEach(h=>{
      const td = document.createElement('td');
      td.contentEditable = true;
      td.textContent = row[h] ?? '';
      td.addEventListener('input', ()=>{
        data[i][h] = td.textContent;
      });
      tr.appendChild(td);
    });
    tbody.appendChild(tr);
  });
}

function selectRow(i){
  selectedRowIndex = i;
  tbody.querySelectorAll('tr').forEach(tr=>tr.classList.remove('selected'));
  const tr = tbody.querySelector(`tr[data-index='${i}']`);
  if(tr) tr.classList.add('selected');
}

function parseCSVText(text, source=''){
  const res = Papa.parse(text, {header:true, skipEmptyLines:true});
  headers = res.meta.fields;
  data = res.data;
  renderTable();
  if(source) setStatus(`Loaded: ${source} (${data.length} rows)`); else setStatus(`Loaded (${data.length} rows)`);
} 

csvFileInput.addEventListener('change', (e)=>{
  const file = e.target.files[0];
  if(!file) return;
  Papa.parse(file, {
    header:true,
    skipEmptyLines:true,
    complete: (results)=>{
      headers = results.meta.fields;
      data = results.data;
      renderTable();
      setStatus(`Loaded file: ${file.name} (${data.length} rows)`);
    }
  });
});

btnFetchServer.addEventListener('click', async ()=>{
  try{
    const resp = await fetch('/api/class_records');
    if(!resp.ok) throw new Error('Not found');
    const text = await resp.text();
    parseCSVText(text, 'server');
  }catch(err){
    setStatus('Server CSV not found. Start the server or use the file input.', false);
  }
});

btnAdd.addEventListener('click', ()=>{
  if(headers.length === 0){
    setStatus('Load a CSV first to know the column headers', false);
    return;
  }
  const obj = {};
  headers.forEach((h, idx)=>{
    if(idx === 0) obj[h] = txtStudentID.value || '';
    else if(idx === 1) obj[h] = txtFirstName.value || '';
    else if(idx === 2) obj[h] = txtLastName.value || '';
    else obj[h] = '';
  });
  data.push(obj);
  renderTable();
  txtStudentID.value = '';
  txtFirstName.value = '';
  txtLastName.value = '';
  setStatus('Row added');
});

btnDelete.addEventListener('click', ()=>{
  if(selectedRowIndex === -1){
    setStatus('Select a row to delete', false);
    return;
  }
  data.splice(selectedRowIndex,1);
  selectedRowIndex = -1;
  renderTable();
  setStatus('Row deleted');
});

btnExport.addEventListener('click', ()=>{
  if(headers.length === 0) return setStatus('No data to export', false);
  const csv = Papa.unparse({fields: headers, data: data.map(r => headers.map(h => r[h] ?? ''))});
  const blob = new Blob([csv], {type: 'text/csv'});
  const url = URL.createObjectURL(blob);
  const a = document.createElement('a');
  a.href = url;
  a.download = 'class_records_export.csv';
  document.body.appendChild(a);
  a.click();
  a.remove();
  URL.revokeObjectURL(url);
  setStatus('CSV exported');
});

btnSaveServer.addEventListener('click', async ()=>{
  if(headers.length === 0) return setStatus('No data to save', false);
  const csv = Papa.unparse({fields: headers, data: data.map(r => headers.map(h => r[h] ?? ''))});
  try {
    const resp = await fetch('/api/class_records', {method:'POST', headers: {'Content-Type':'text/csv'}, body: csv});
    if(!resp.ok) throw new Error(await resp.text());
    setStatus('Saved to server ✅');
  } catch(err){
    setStatus('Save failed: ' + (err.message || err), false);
  }
});

// Try to auto-load the CSV from server (if server is running)
fetch('/api/class_records').then(r=>{ if(r.ok) r.text().then(text=>parseCSVText(text, 'server')); }).catch(()=>{});
