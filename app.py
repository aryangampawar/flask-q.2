from flask import Flask, render_template, request

app = Flask(__name__)

@app.route('/', methods=['GET', 'POST'])
def register():
    if request.method == 'POST':
        name = request.form['name']
        email = request.form['email']
        return f"Thank you {name}, you have registered successfully!"
    return '''
        <form method="POST">
            Name: <input type="text" name="name"><br>
            Email: <input type="email" name="email"><br>
            <input type="submit" value="Register">
        </form>
    '''

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
