function addInputRow() {
    const rowContainer = document.getElementById('inputRows');
    const row = document.createElement('div');
    row.className = 'row g-2 mb-2 align-items-center';
    row.innerHTML = `
        <div class="col-md-2">
            <label for="inputNumericalValue" class="form-label">Numerical Input:</label>
            <input type="number" id="inputNumericalValue" class="form-control" name="inputNumericalValue" step="any" required>
        </div>
        <div class="col-md-2">
            <label for="inputUom" class="form-label">Input UOM:</label>
            <input type="text" id="inputUom" list="uom-options" class="form-control" name="inputUom" required>
        </div>
        <div class="col-md-2">
            <label for="targetUom" class="form-label">Target UOM:</label>
            <input type="text" id="targetUom" list="uom-options" class="form-control" name="targetUom" required>
        </div>
        <div class="col-md-2">
            <label for="studentAnswer" class="form-label">Student Answer:</label>
            <input type="text" id="studentAnswer" class="form-control" name="studentAnswer" step="any" required>
        </div>
        <div class="col-md-2">
            <label for="responseField" class="form-label">Response:</label>
            <textarea id="responseField" name="responseField" class="form-control" rows="1" disabled></textarea>
        </div>
        <div class="col-md-2 text-center">
            <label for="removeButton" class="form-label" style="color: white;">Remove button:</label>
            <button type="button" id="removeButton" class="btn btn-danger" onclick="removeRow(this)">Remove</button>
        </div>
    `;
    rowContainer.appendChild(row);
}

function removeRow(button) {
    button.closest('.row').remove();
}

async function sendRequest() {
    const rows = document.querySelectorAll('#inputRows .row');

    for (const row of rows) {
        const payload = {
            inputNumericalValue: parseFloat(row.querySelector('[name="inputNumericalValue"]').value) || 0,
            inputUom: row.querySelector('[name="inputUom"]').value,
            targetUom: row.querySelector('[name="targetUom"]').value,
            studentAnswer: row.querySelector('[name="studentAnswer"]').value
        };

        try {
            const response = await fetch('http://localhost:8080/uom-conversion-validation', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(payload)
            });

            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            const responseData = await response.json();
            row.querySelector('[name="responseField"]').value = responseData.validationOutput;

        } catch (error) {
            console.error('Error:', error);
            row.querySelector('[name="responseField"]').value = `Error: ${error.message}`;
        }
    }
}