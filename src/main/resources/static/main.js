const form = document.getElementById('shortenForm');
const urlInput = document.getElementById('url');
const daysInput = document.getElementById('days');
const tbody = document.querySelector('#links tbody');

async function fetchLinks(){
  const res = await fetch('/api/links');
  const data = await res.json();
  tbody.innerHTML = '';
  data.forEach(row => {
    const tr = document.createElement('tr');
    const shortUrl = location.origin + '/api/r/' + row.code;
    tr.innerHTML = `<td><a target="_blank" href="${shortUrl}">${row.code}</a></td>
                    <td style="max-width:520px; overflow:hidden; text-overflow:ellipsis;">${row.target}</td>
                    <td>${row.clickCount}</td>
                    <td><button data-id="${row.id}" class="delete">Delete</button></td>`;
    tbody.appendChild(tr);
  });
}

form.addEventListener('submit', async (e)=>{
  e.preventDefault();
  const payload = { url: urlInput.value.trim(), expiresInDays: daysInput.value ? Number(daysInput.value) : null };
  const res = await fetch('/api/shorten', { method:'POST', headers:{'Content-Type':'application/json'}, body: JSON.stringify(payload) });
  if(res.ok){ urlInput.value=''; daysInput.value=''; await fetchLinks(); }
});

tbody.addEventListener('click', async (e)=>{
  const btn = e.target.closest('button.delete');
  if(!btn) return;
  const id = btn.getAttribute('data-id');
  const res = await fetch('/api/links/'+id, { method:'DELETE' });
  if(res.status === 204) await fetchLinks();
});

fetchLinks();


