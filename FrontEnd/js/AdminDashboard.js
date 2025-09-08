
    // Global variables
    let currentHotelId = null;
    let imageUrl = "assets/images/";
    
    $(document).ready(function () {
        // Check authentication
        let token = localStorage.getItem("jwtToken");
        if (!token) {
            alert("You are not logged in!");
            window.location.href = "login.html";
            return;
        }
        
        // Load hotels
        loadHotels();
        
        // Set up event handlers
        $('#btnSave').click(saveHotel);
        $('#btnUpdate').click(updateHotel);
    });
    
    function loadHotels() {
        let token = localStorage.getItem("jwtToken");
        
        $.ajax({
            url: "http://localhost:8080/api/v1/hotels/all",
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`
            },
            success: (hotels) => {
                let table = $('#HotelTable');
                table.empty();

                if (hotels && Array.isArray(hotels)) {
                    hotels.forEach((hotel) => {
                        let image = imageUrl + (hotel.image || 'default.jpg');
                        
                        table.append(`
                            <tr>
                                <td>${hotel.id}</td>
                                <td>${hotel.name}</td>
                                <td>${hotel.location}</td>
                                <td class="text-truncate" style="max-width: 200px;">${hotel.description || ''}</td>
                                <td class="text-truncate" style="max-width: 150px;">${hotel.amenities || ''}</td>
                                <td>${hotel.phoneNumber}</td>
                                <td>
                                    <img src="${image}" width="80" height="80" alt="Hotel Image"
                                    onerror="this.onerror=null; this.src='assets/images/default.jpg';">
                                </td>
                                <td class="action-buttons">
                                    <button type="button" class="btn btn-sm btn-primary"
                                    data-bs-toggle="modal" data-bs-target="#updateModal"
                                    onclick="prepareEdit('${hotel.id}', '${hotel.name}', '${hotel.location}', 
                                    '${hotel.description || ''}', '${hotel.amenities || ''}', 
                                    '${hotel.phoneNumber}', '${hotel.image || ''}')">
                                        Edit
                                    </button>
                                    <button type="button" class="btn btn-sm btn-danger" 
                                    onclick="confirmDelete('${hotel.id}', '${hotel.name}')">
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        `);
                    });
                } else {
                    table.append('<tr><td colspan="8" class="text-center">No hotels found</td></tr>');
                }
            },
            error: (error) => {
                console.error("AJAX Error:", error);
                if (error.status === 403) {
                    alert("You don't have permission to access hotel data.");
                } else {
                    alert("Something went wrong while fetching hotels.");
                }
            }
        });
    }
    
    function saveHotel() {
        let name = $("#name").val().trim();
        let location = $("#location").val().trim();
        let description = $("#description").val().trim();
        let amenities = $("#amenities").val().trim();
        let phoneNumber = $("#PhoneNumber").val().trim();
        let imageInput = $("#imageUpload")[0];
        let fileName = imageInput.files.length > 0 ? imageInput.files[0].name : "default.jpg";

        if (!name || !location || !phoneNumber) {
            alert("Please fill in all the required fields.");
            return;
        }

        let token = localStorage.getItem("jwtToken");
        
        $.ajax({
            url: "http://localhost:8080/api/v1/hotels/save",
            method: "POST",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            },
            data: JSON.stringify({
                "name": name,
                "location": location,
                "description": description,
                "amenities": amenities,
                "phoneNumber": phoneNumber,
                "image": fileName
            }),
            success: (savedHotel) => {
                alert("Hotel added successfully");
                $("#exampleModal").modal("hide");
                $("#addHotelForm")[0].reset();
                loadHotels();
            },
            error: (error) => {
                console.error(error);
                if (error.status === 403) {
                    alert("You don't have permission to add hotels.");
                } else {
                    alert("Something went wrong while saving the hotel.");
                }
            }
        });
    }
    
    function prepareEdit(id, name, location, description, amenities, phoneNumber, image) {
        currentHotelId = id;
        
        $("#Update_name").val(name);
        $("#Update_location").val(location);
        $("#Update_description").val(description);
        $("#Update_amenities").val(amenities);
        $("#Update_PhoneNumber").val(phoneNumber);
        
        // Show current image
        let imageSrc = imageUrl + (image || 'default.jpg');
        $("#Update_currentImage").attr("src", imageSrc).on("error", function() {
            $(this).attr("src", "assets/images/default.jpg");
        });
        
        // Clear file input
        $("#Update_imageUpload").val("");
    }
    
    function updateHotel() {
        if (!currentHotelId) {
            alert("No hotel selected for update.");
            return;
        }
        
        let name = $("#Update_name").val().trim();
        let location = $("#Update_location").val().trim();
        let description = $("#Update_description").val().trim();
        let amenities = $("#Update_amenities").val().trim();
        let phoneNumber = $("#Update_PhoneNumber").val().trim();
        let imageInput = document.getElementById("Update_imageUpload");
        let fileName = imageInput.files.length > 0 ? imageInput.files[0].name : $("#Update_currentImage").attr("src").split('/').pop();

        if (!name || !location || !phoneNumber) {
            alert("Please fill in all the required fields.");
            return;
        }

        let token = localStorage.getItem("jwtToken");
        let hotelData = {
            "id": currentHotelId,
            "name": name,
            "location": location,
            "description": description,
            "amenities": amenities,
            "phoneNumber": phoneNumber,
            "image": fileName
        };
        
        $.ajax({
            url: "http://localhost:8080/api/v1/hotels/update/" + currentHotelId,
            method: "PUT",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            },
            data: JSON.stringify(hotelData),
            success: (updatedHotel) => {
                alert("Hotel updated successfully!");
                $("#updateModal").modal("hide");
                loadHotels();
            },
            error: (error) => {
                console.error(error);
                if (error.status === 403) {
                    alert("You don't have permission to update hotels.");
                } else {
                    alert("Something went wrong while updating the hotel.");
                }
            }
        });
    }
    
    function confirmDelete(id, name) {
        if (confirm(`Are you sure you want to delete the hotel "${name}"?`)) {
            deleteHotel(id);
        }
    }
    
    function deleteHotel(id) {
        let token = localStorage.getItem("jwtToken");
        
        $.ajax({
            url: `http://localhost:8080/api/v1/hotels/delete/${id}`,
            method: "DELETE",
            headers: {
                "Authorization": `Bearer ${token}`
            },
            success: (response) => {
                alert("Hotel deleted successfully");
                loadHotels();
            },
            error: (error) => {
                console.error(error);
                if (error.status === 403) {
                    alert("You don't have permission to delete hotels.");
                } else {
                    alert("Something went wrong while deleting the hotel.");
                }
            }
        });
    }
    
    function searchHotel() {
        let input = document.getElementById("search").value.toLowerCase();
        let table = document.getElementById("HotelTable");
        let rows = table.getElementsByTagName("tr");

        for (let i = 0; i < rows.length; i++) {
            let cols = rows[i].getElementsByTagName("td");
            if (cols.length > 0) {
                let hotelName = cols[1].textContent.toLowerCase();
                let hotelLocation = cols[2].textContent.toLowerCase();
                
                if (hotelName.includes(input) || hotelLocation.includes(input)) {
                    rows[i].style.display = "";
                } else {
                    rows[i].style.display = "none";
                }
            }
        }
    }
    
    function logout() {
        if (confirm("Are you sure you want to logout?")) {
            localStorage.removeItem("jwtToken");
            alert("Logged out successfully");
            window.location.href = "Login.html";
        }
    }

