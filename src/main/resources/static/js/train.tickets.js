// 기차표 리스트
function displayTrainTickets(tickets) {
    var ticketsTable = document.getElementById("ticketsTable").getElementsByTagName('tbody')[0];
    ticketsTable.innerHTML = ""; // Clear existing tickets

    tickets.forEach(ticket => {
        var row = ticketsTable.insertRow();
        
        var cellTrainNumber = row.insertCell(0);
        var cellDepartureStation = row.insertCell(1);
        var cellArrivalStation = row.insertCell(2);
        var cellDepartureTime = row.insertCell(3);
        var cellArrivalTime = row.insertCell(4);
        var cellPrice = row.insertCell(5);
        var cellSelect = row.insertCell(6);

        cellTrainNumber.innerHTML = ticket.trainNumber;
        cellDepartureStation.innerHTML = ticket.departureStation;
        cellArrivalStation.innerHTML = ticket.arrivalStation;
        cellDepartureTime.innerHTML = ticket.departureTime;
        cellArrivalTime.innerHTML = ticket.arrivalTime;
        cellPrice.innerHTML = ticket.price;

        var selectButton = document.createElement("button");
        selectButton.innerHTML = "Select";
        selectButton.onclick = function() {
            // Add your logic for when a ticket is selected
            console.log("Selected Ticket:", ticket);
        };
        cellSelect.appendChild(selectButton);
    });
}
// 기차표 선택
function selectTicket(ticket) {
    console.log("Selected Ticket:", ticket);
    // Additional logic for handling the selected ticket
}

