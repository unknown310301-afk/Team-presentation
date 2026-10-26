let menu=[],cart={},category='All',lastOrderId=null;
const money=n=>'₹'+Number(n).toFixed(2);
async function api(url,options={}){const r=await fetch(url,{headers:{'Content-Type':'application/json'},...options});if(!r.ok){let e={};try{e=await r.json()}catch{}throw new Error(e.error||`Request failed (${r.status})`)}if(r.status===204)return null;return r.json()}
function toast(s){let t=document.querySelector('#toast');t.textContent=s;t.style.display='block';setTimeout(()=>t.style.display='none',2800)}
async function loadMenu(){try{menu=await api('/api/foods');renderFilters();renderMenu()}catch(e){document.querySelector('#menuGrid').innerHTML='<p>Could not load menu. Please refresh after the server starts.</p>';toast(e.message)}}
function renderFilters(){let cats=['All',...new Set(menu.map(f=>f.category))];document.querySelector('#filters').innerHTML=cats.map(c=>`<button class="filter ${category===c?'active':''}" onclick="setCategory('${esc(c)}')">${esc(c)}</button>`).join('')}
function setCategory(c){category=c;renderFilters();renderMenu()}
function esc(s=''){return String(s).replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]))}
function renderMenu() {
    const q = document.querySelector('#search').value.toLowerCase();

    const list = menu.filter(f =>
        (category === 'All' || f.category === category) &&
        f.name.toLowerCase().includes(q)
    );

    const images = {
        'Classic Burger': '/images/burger.jpg',
        'Margherita Pizza': '/images/pizza.jpg',
        'Crispy Fries': '/images/fries.jpg',
        'Paneer Wrap': '/images/wrap.jpg',
        'Chocolate Shake': '/images/shake.jpg',
        'Veggie Bowl': '/images/bowl.jpg'
    };

    document.querySelector('#menuGrid').innerHTML = list.length
        ? list.map(f => `
            <article class="food-card">
                <div class="food-art">
                    <img
                        src="${images[f.name] || ''}"
                        alt="${esc(f.name)}"
                        onerror="this.style.display='none'; this.parentElement.textContent='🍽️';"
                    >
                </div>

                <div class="food-info">
                    <small>${esc(f.category)}</small>
                    <h3>${esc(f.name)}</h3>
                    <p>${esc(f.description || 'Freshly prepared just for you.')}</p>

                    <div class="food-bottom">
                        <span class="price">${money(f.price)}</span>
                        <button class="add" onclick="addToCart(${f.id})">
                            + Add
                        </button>
                    </div>
                </div>
            </article>
        `).join('')
        : '<p>No dishes match your search.</p>';
}
function addToCart(id){cart[id]=(cart[id]||0)+1;drawCart();toast('Added to your basket!')}
function removeOne(id){if(cart[id]>1)cart[id]--;else delete cart[id];drawCart()}
function drawCart(){let entries=Object.entries(cart).map(([id,q])=>({food:menu.find(f=>f.id===Number(id)),q})).filter(x=>x.food);let sub=entries.reduce((s,x)=>s+Number(x.food.price)*x.q,0),tax=sub*.05,delivery=30;document.querySelector('#cartCount').textContent=entries.reduce((s,x)=>s+x.q,0);document.querySelector('#cartItems').innerHTML=entries.length?entries.map(x=>`<div class="cart-line"><span>${esc(x.food.emoji)} ${esc(x.food.name)} × ${x.q}</span><span>${money(Number(x.food.price)*x.q)} <button type="button" onclick="removeOne(${x.food.id})">−</button></span></div>`).join(''):'Your cart is waiting for something tasty.';document.querySelector('#subtotal').textContent=money(sub);document.querySelector('#tax').textContent=money(tax);document.querySelector('#delivery').textContent=money(delivery);document.querySelector('#total').textContent=money(sub+tax+delivery)}
document.querySelector('#orderForm').addEventListener('submit',async e=>{e.preventDefault();if(!Object.keys(cart).length){toast('Add at least one item first.');return}let f=new FormData(e.target),payload={customerName:f.get('customerName'),customerEmail:f.get('customerEmail'),deliveryAddress:f.get('deliveryAddress'),items:Object.entries(cart).map(([foodItemId,quantity])=>({foodItemId:Number(foodItemId),quantity}))};try{let o=await api('/api/orders',{method:'POST',body:JSON.stringify(payload)});lastOrderId=o.id;cart={};drawCart();e.target.reset();document.querySelector('#tracking').hidden=false;document.querySelector('#trackingText').textContent=`Order #${o.id} • Total ${money(o.total)} • We'll deliver to ${o.deliveryAddress}.`;document.querySelector('#statusSelect').value=o.status;renderStatus(o.status);document.querySelector('#tracking').scrollIntoView({behavior:'smooth'});toast('Order placed successfully!')}catch(err){toast(err.message)}});
function renderStatus(current){let states=['PLACED','CONFIRMED','PREPARING','OUT_FOR_DELIVERY','DELIVERED'];document.querySelector('#statusTrack').innerHTML=states.map((s,i)=>`<span class="status-step ${states.indexOf(current)>=i?'done':''}">${['🧾','✅','👨‍🍳','🛵','🎉'][i]} ${s.replaceAll('_',' ')}</span>`).join('');if(current==='CANCELLED')document.querySelector('#statusTrack').innerHTML+='<span class="status-step done">✖ CANCELLED</span>'}
document.querySelector('#updateStatus').addEventListener('click',async()=>{if(!lastOrderId)return;try{let s=document.querySelector('#statusSelect').value;let o=await api(`/api/orders/${lastOrderId}/status?status=${s}`,{method:'PUT'});renderStatus(o.status);toast('Order status updated.')}catch(e){toast(e.message)}});
loadMenu();