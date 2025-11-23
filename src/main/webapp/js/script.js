document.addEventListener('DOMContentLoaded', function () {

    // --- Dynamic Lottie Animation ---
    const weatherContainer = document.getElementById('lottie-weather');
    if (weatherContainer) {
        const weatherMain = weatherContainer.getAttribute('data-weather').toLowerCase();
        let animationUrl = 'https://assets2.lottiefiles.com/packages/lf20_xlkxtmul.json'; // Default Sunny/Cloudy

        if (weatherMain.includes('rain') || weatherMain.includes('drizzle')) {
            animationUrl = 'https://assets7.lottiefiles.com/packages/lf20_b88nh30c.json'; // Rain
        } else if (weatherMain.includes('thunderstorm')) {
            animationUrl = 'https://assets1.lottiefiles.com/packages/lf20_kuotozkc.json'; // Thunder
        } else if (weatherMain.includes('snow')) {
            animationUrl = 'https://assets8.lottiefiles.com/packages/lf20_rh7j6qgq.json'; // Snow
        } else if (weatherMain.includes('clear')) {
            animationUrl = 'https://assets2.lottiefiles.com/packages/lf20_xlkxtmul.json'; // Sunny
        } else if (weatherMain.includes('clouds')) {
            animationUrl = 'https://assets4.lottiefiles.com/packages/lf20_kxsd2ytq.json'; // Cloudy
        }

        lottie.loadAnimation({
            container: weatherContainer,
            renderer: 'svg',
            loop: true,
            autoplay: true,
            path: animationUrl
        });
    }

    // --- High Accuracy Geolocation for Weather App ---
    const locationBtn = document.getElementById('location-btn');
    let bestAccuracy = Infinity;
    let bestPos = null;
    let watchId = null;
    let locationTimeout = null;

    function finishLocation() {
        if (watchId) navigator.geolocation.clearWatch(watchId);
        if (locationTimeout) clearTimeout(locationTimeout);

        if (bestPos) {
            const lat = bestPos.coords.latitude;
            const lon = bestPos.coords.longitude;
            window.location.href = `weather?lat=${lat}&lon=${lon}`;
        } else {
            alert("Could not determine accurate location.");
            locationBtn.innerHTML = '<i class="fas fa-location-arrow"></i>';
        }
    }

    if (locationBtn) {
        locationBtn.addEventListener('click', (e) => {
            e.preventDefault();

            if (!navigator.geolocation) {
                alert("Geolocation not supported by browser.");
                return;
            }

            // Start loading animation
            locationBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i>';

            bestAccuracy = Infinity;
            bestPos = null;

            // EXACT SAME LOGIC AS YOUR WORKING CODE
            watchId = navigator.geolocation.watchPosition(
                (pos) => {
                    const acc = pos.coords.accuracy;

                    // Keep best reading
                    if (acc < bestAccuracy) {
                        bestAccuracy = acc;
                        bestPos = pos;
                    }

                    // Stop early if accurate enough
                    if (acc < 30) {
                        finishLocation();
                    }
                },
                (err) => {
                    console.error(err);
                    alert("Location access denied.");
                    locationBtn.innerHTML = '<i class="fas fa-location-arrow"></i>';
                },
                {
                    enableHighAccuracy: true,
                    timeout: 10000,
                    maximumAge: 0
                }
            );

            // Stop after 10 seconds if accuracy not good
            locationTimeout = setTimeout(() => {
                finishLocation();
            }, 10000);
        });
    }

    // --- Search Autocomplete ---
    const cityInput = document.getElementById('city-input');
    const suggestionsBox = document.getElementById('suggestions');
    const form = document.querySelector('.search-box');
    let debounceTimer;

    if (cityInput && suggestionsBox && form) {
        cityInput.addEventListener('input', function () {
            const query = this.value;
            clearTimeout(debounceTimer);

            if (query.length < 3) {
                suggestionsBox.style.display = 'none';
                return;
            }

            debounceTimer = setTimeout(() => {
                fetch(`api/search?q=${query}`)
                    .then(response => response.json())
                    .then(data => {
                        suggestionsBox.innerHTML = '';
                        if (data.length > 0) {
                            suggestionsBox.style.display = 'block';
                            data.forEach(city => {
                                const div = document.createElement('div');
                                div.className = 'suggestion-item';
                                div.textContent = `${city.name}, ${city.country}`;
                                div.addEventListener('click', () => {
                                    cityInput.value = city.name;
                                    suggestionsBox.style.display = 'none';
                                    form.submit();
                                });
                                suggestionsBox.appendChild(div);
                            });
                        } else {
                            suggestionsBox.style.display = 'none';
                        }
                    })
                    .catch(err => console.error(err));
            }, 300);
        });

        // Close suggestions when clicking outside
        document.addEventListener('click', function (e) {
            if (e.target !== cityInput && e.target !== suggestionsBox) {
                suggestionsBox.style.display = 'none';
            }
        });
    }
});
