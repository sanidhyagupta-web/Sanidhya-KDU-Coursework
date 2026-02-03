(() => {
	const barsEl = document.getElementById('bars');
	const chartContainer = document.getElementById('chartContainer');
	const historyList = document.getElementById('historyList');
	const qtyInput = document.getElementById('qtyInput');
	const buyBtn = document.getElementById('buyBtn');
	const sellBtn = document.getElementById('sellBtn');
	const priceVal = document.querySelector('.price-val');
	const priceChange = document.querySelector('.price-change');

	let history = [];
	let lastPrice = Math.floor(Math.random() * 501); 
    
    const green = '#24a744';
    const red = '#c62828';
    const neutral = '#444';
    const lightNeutral = '#666';

    const timeForNextTick = 5000;

	function fmtTime(d = new Date()){
		return d.toLocaleTimeString();
	}

	function maxBarsAllowed(){
		const w = chartContainer.clientWidth || 500;
		return Math.floor(w / 20); 
	}

	function updatePriceDisplay(price){
		const diff = price - lastPrice;
		priceVal.textContent = price.toFixed(2);
		if(diff > 0){
			priceVal.style.color = green;
			priceChange.textContent = `+${diff.toFixed(2)}`;
			priceChange.style.color = green;
		} else if(diff < 0){
			priceVal.style.color = red;
			priceChange.textContent = `${diff.toFixed(2)}`;
			priceChange.style.color = red;
		} else {
			priceVal.style.color = neutral;
			priceChange.textContent = '--';
			priceChange.style.color = lightNeutral;
		}
	}

	function addBar(price, positive){
		const bar = document.createElement('div');
		bar.className = 'bar ' + (positive ? 'positive' : 'negative');
		bar.style.height = Math.max(2, Math.min(500, Math.round(price))) + 'px';
		bar.title = `${price} @ ${fmtTime()}`;
		barsEl.appendChild(bar);
		const max = maxBarsAllowed();
		while(barsEl.children.length > max){
			barsEl.removeChild(barsEl.firstChild);
		}
	}

	function pushHistory(action, qty, price){
		const item = {action, qty, price, time: new Date()};
		history.unshift(item);
		renderHistory();
	}

	function renderHistory(){
		historyList.innerHTML = '';
		history.forEach(h => {
			const el = document.createElement('div');
			el.className = 'history-item';
			const left = document.createElement('div');
			left.innerHTML = `<div><strong>${h.qty} stocks</strong></div><div class="meta">${fmtTime(h.time)}</div>`;
			const right = document.createElement('div');
			right.innerHTML = `<div class="meta">@ ${h.price.toFixed(2)}</div><div class="action ${h.action.toLowerCase()}">${h.action}</div>`;
			el.appendChild(left);
			el.appendChild(right);
			historyList.appendChild(el);
		});
	}

	function tick(){
		const newPrice = Math.floor(Math.random() * 501);
		const positive = newPrice >= lastPrice;
		addBar(newPrice, positive);
		updatePriceDisplay(newPrice);
		lastPrice = newPrice;
	}

	buyBtn.addEventListener('click', () => {
		const qty = parseInt(qtyInput.value, 10);
		if(!qty || qty <= 0){
             alert('Enter a valid quantity'); 
             return; 
        }
		pushHistory('Buy', qty, lastPrice);
	});

	sellBtn.addEventListener('click', () => {
		const qty = parseInt(qtyInput.value, 10);
		if(!qty || qty <= 0){
             alert('Enter a valid quantity'); 
             return; 
        }
		pushHistory('Sell', qty, lastPrice);
	});

	window.addEventListener('load', () => {
		addBar(lastPrice, true);
		updatePriceDisplay(lastPrice);
		setInterval(tick, timeForNextTick);
		window.addEventListener('resize', () => {
			const max = maxBarsAllowed();
			while(barsEl.children.length > max){ 
                barsEl.removeChild(barsEl.firstChild); 
            }
		});
	});

})();
