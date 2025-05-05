const tabs = document.querySelectorAll(".tab");
const contents = document.querySelectorAll(".content");

tabs.forEach(tab => {
    tab.addEventListener("click", () => {
        tabs.forEach(t => t.classList.remove("active"));
        contents.forEach(c => c.classList.remove("active"));
        tab.classList.add("active");
        document.getElementById(tab.dataset.target).classList.add("active");
    });
});

document.getElementById("registerForm").addEventListener("submit", async function (e) {
    e.preventDefault();
    const responseElement = document.getElementById("response");
    responseElement.innerText = "Processing...";
    const payload = {
        username: document.getElementById("username").value,
        password: document.getElementById("password").value,
        email: document.getElementById("email").value
    };

    try {
        const res = await fetch("/api/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });
        const data = await res.json();
        responseElement.innerText = res.ok
            ? "✅ Registered successfully as " + data.username
            : "❌ Error: " + data.userResponseType;
    } catch (error) {
        responseElement.innerText = "❌ Network or server error";
    }
});

async function fetchPrices() {
    try {
        const res = await fetch("/api/prices");
        const data = await res.json();
        const prices = data.prices;
        const tableBody = document.getElementById("priceTableBody");
        const buyDropdown = document.getElementById("buySymbol");
        const sellDropdown = document.getElementById("sellSymbol");
        tableBody.innerHTML = "";
        buyDropdown.innerHTML = "";
        sellDropdown.innerHTML = "";

        for (const symbol in prices) {
            const row = document.createElement("tr");
            row.innerHTML = `<td>${symbol}</td><td>${prices[symbol]}</td>`;
            tableBody.appendChild(row);

            const option = document.createElement("option");
            option.value = symbol;
            option.text = symbol;
            buyDropdown.appendChild(option.cloneNode(true));
            sellDropdown.appendChild(option.cloneNode(true));
        }
    } catch (e) {
        console.error("Failed to fetch prices:", e);
    }
}
setInterval(fetchPrices, 1000);
async function fetchPrices() {
    try {
        const res = await fetch("/api/prices");
        const data = await res.json();
        const prices = data.prices;

        const priceTable = document.getElementById("priceTable");
        priceTable.innerHTML = ""; // Clear existing content

        const table = document.createElement("table");
        const header = document.createElement("tr");
        header.innerHTML = "<th>Symbol</th><th>Price (USD)</th>";
        table.appendChild(header);

        for (const [symbol, price] of Object.entries(prices)) {
            const row = document.createElement("tr");
            row.innerHTML = `<td>${symbol}</td><td>${price.toFixed(4)}</td>`;
            table.appendChild(row);
        }

        priceTable.appendChild(table);
    } catch (err) {
        console.error("Failed to fetch prices:", err);
        document.getElementById("priceTable").innerText = "Failed to load prices.";
    }
}




document.getElementById("buyForm").addEventListener("submit", async function (e) {
    e.preventDefault();
    const buyResponse = document.getElementById("buyResponse");
    buyResponse.innerText = "Processing...";
    const payload = {
        username: document.getElementById("buyUsername").value,
        password: document.getElementById("buyPassword").value,
        symbol: document.getElementById("buySymbol").value,
        quantity: document.getElementById("buyQuantity").value
    };

    try {
        const res = await fetch("/api/buy", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });
        const data = await res.json();
        buyResponse.innerText = res.ok ? "✅ Buy successful" : "❌ Error: " + data.message;
    } catch (error) {
        buyResponse.innerText = "❌ Network or server error";
    }
});

document.getElementById("sellForm").addEventListener("submit", async function (e) {
    e.preventDefault();
    const sellResponse = document.getElementById("sellResponse");
    sellResponse.innerText = "Processing...";
    const payload = {
        username: document.getElementById("sellUsername").value,
        password: document.getElementById("sellPassword").value,
        symbol: document.getElementById("sellSymbol").value,
        quantity: document.getElementById("sellQuantity").value
    };

    try {
        const res = await fetch("/api/sell", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        });
        const data = await res.json();
        sellResponse.innerText = res.ok ? "✅ Sell successful" : "❌ Error: " + data.message;
    } catch (error) {
        sellResponse.innerText = "❌ Network or server error";
    }
});

async function populateCustomDropdown(containerId, hiddenInputId, symbols) {
    const container = document.getElementById(containerId);
    const hiddenInput = document.getElementById(hiddenInputId);

    const selectedDiv = document.createElement("div");
    selectedDiv.className = "dropdown-selected";
    selectedDiv.textContent = "Select a symbol";
    container.appendChild(selectedDiv);

    const dropdownList = document.createElement("div");
    dropdownList.className = "dropdown-list";
    container.appendChild(dropdownList);

    for (const { symbol, name, iconURL } of symbols) {
        const item = document.createElement("div");
        item.className = "dropdown-item";

        const img = document.createElement("img");
        img.src = iconURL;
        img.alt = name;

        item.appendChild(img);
        item.appendChild(document.createTextNode(`${symbol} – ${name}`));

        item.addEventListener("click", () => {
            hiddenInput.value = symbol;
            selectedDiv.textContent = `${symbol} – ${name}`;
            dropdownList.style.display = "none";
        });

        dropdownList.appendChild(item);
    }

    selectedDiv.addEventListener("click", () => {
        dropdownList.style.display = dropdownList.style.display === "block" ? "none" : "block";
    });

    // Close on outside click
    document.addEventListener("click", (e) => {
        if (!container.contains(e.target)) {
            dropdownList.style.display = "none";
        }
    });
}

async function populateSymbolDropdowns() {
    try {
        const res = await fetch("/api/availableSymbols");
        const data = await res.json();
        const symbols = data.symbols || [];

        await populateCustomDropdown("buyDropdownContainer", "buySymbol", symbols);
        await populateCustomDropdown("sellDropdownContainer", "sellSymbol", symbols);
    } catch (err) {
        console.error("Failed to load dropdown symbols", err);
    }
}

// Tab switching logic
document.addEventListener("DOMContentLoaded", () => {
    const tabs = document.querySelectorAll(".tab-button");
    const contents = document.querySelectorAll(".tab-content");

    tabs.forEach(tab => {
        tab.addEventListener("click", () => {
            const targetId = tab.getAttribute("data-tab");

            contents.forEach(content => {
                content.style.display = content.id === targetId ? "block" : "none";
            });

            tabs.forEach(t => t.classList.remove("active"));
            tab.classList.add("active");
        });
    });

    populateSymbolDropdowns();  // also run your dropdown loader
    fetchPrices();              // if you have a prices updater

    setInterval(fetchPrices, 1000); // keep price updates going
});


