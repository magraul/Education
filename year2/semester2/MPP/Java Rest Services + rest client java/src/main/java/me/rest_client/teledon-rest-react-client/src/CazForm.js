
import React from  'react';
class CazForm extends React.Component{

    constructor(props) {
        super(props);
        this.state = {description:''};
    }


    handleDescChange=(event) =>{
        console.log('ai scris');
        this.setState({description: event.target.value});
    }


    handleSubmit = (event) => {

        console.log("suybmit")
        let descrieree = document.getElementById("desc");
        if (descrieree.value === "") {
            alert("Nu ati introdus descrierea!")
        }else {

            var caz = {
                description: this.state.description
            }

            console.log(caz);
            this.props.addF(caz);
            event.preventDefault();
        }
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    Descriere:
                    <input id="desc" type="text" value={this.state.description} onChange={this.handleDescChange} />
                </label><br/>

                <input type="submit" value="Adauga" />
            </form>
        );
    }
}
export default CazForm;