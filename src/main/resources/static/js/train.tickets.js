/* train.ticket.js */
// Global variables to keep track of the current page and size
var currentPage = 1;
var pageSize = 10; // You can adjust the page size as needed

// Function to load the next page of train tickets
function loadNextPage(depPlaceId, arrPlaceId, depPlandTime) {
    currentPage++; // Increment the current page

    // Additional check for required elements
    if (!depPlaceId || !arrPlaceId || !depPlandTime) {
        console.error("One or more required elements not found.");
        return;
    }

    // Make an AJAX request to fetch the next page of train tickets
    fetchTrainTickets(depPlaceId, arrPlaceId, depPlandTime, currentPage, pageSize);
}

// Function to make an AJAX request to fetch train tickets
function fetchTrainTickets(depPlaceId, arrPlaceId, depPlandTime, page, size) {
    // Construct the URL with the parameters
    var url = '/train/trainInfoPaginated?depPlaceId=' + depPlaceId + '&arrPlaceId=' + arrPlaceId + '&depPlandTime=' + depPlandTime + '&page=' + page + '&size=' + size;

    // Make the AJAX request
    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok: ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            // Handle the fetched data, update the UI, etc.
            updateTrainTicketList(data);
        })
        .catch(error => {
            console.error('Error fetching train tickets:', error);
        });
}

// Function to update the UI with the fetched train ticket data
function updateTrainTicketList(data) {
    // Assuming your data structure is similar to the previous train ticket display logic

    // Update the HTML to display the new train ticket data
    var trainTableBody = document.getElementById('trainTableBody');
    // Clear the existing table body content if needed

    data.response.body.items.item.forEach(train => {
        var row = document.createElement('tr');
        // Create and append table cells with train information
        // ...

        trainTableBody.appendChild(row);
    });
}

// Example: Attach the loadNextPage function to a "Next" button click event
document.getElementById('nextButton').addEventListener('click', function() {
    loadNextPage();
});

document.addEventListener('DOMContentLoaded', function() {
    // Your existing JavaScript code, including the loadNextPage function
});