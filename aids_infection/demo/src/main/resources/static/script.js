document.getElementById('predictionForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Mencegah form untuk submit secara default

    // Mengambil nilai dari setiap input form
    const formData = {
        time: parseFloat(document.getElementById('time').value),
        trt: parseInt(document.getElementById('trt').value),
        age: parseFloat(document.getElementById('age').value),
        wtkg: parseFloat(document.getElementById('wtkg').value),
        hemo: parseInt(document.getElementById('hemo').value),
        homo: parseInt(document.getElementById('homo').value),
        drugs: parseInt(document.getElementById('drugs').value),
        karnof: parseFloat(document.getElementById('karnof').value),
        oprior: parseInt(document.getElementById('oprior').value),
        z30: parseInt(document.getElementById('z30').value),
        preanti: parseFloat(document.getElementById('preanti').value),
        race: parseInt(document.getElementById('race').value),
        gender: parseInt(document.getElementById('gender').value),
        str2: parseInt(document.getElementById('str2').value),
        strat: parseInt(document.getElementById('strat').value),
        symptom: parseInt(document.getElementById('symptom').value),
        treat: parseInt(document.getElementById('treat').value),
        offtrt: parseInt(document.getElementById('offtrt').value),
        cd40: parseFloat(document.getElementById('cd40').value),
        cd420: parseFloat(document.getElementById('cd420').value),
        cd80: parseFloat(document.getElementById('cd80').value),
        cd820: parseFloat(document.getElementById('cd820').value)
    };

    // Mengirimkan data ke backend dengan menggunakan fetch API
    fetch('http://localhost:8080/predict/aids', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
    .then(response => response.json())
    .then(data => {
        // Menampilkan hasil prediksi
        if (data.status === 'success') {
            document.getElementById('predictionResult').textContent = data.prediction;
        } else {
            document.getElementById('predictionResult').textContent = 'Error: ' + data.message;
        }
    })
    .catch(error => {
        // Menangani error jika ada masalah dengan permintaan
        document.getElementById('predictionResult').textContent = 'Error: ' + error.message;
    });
});
