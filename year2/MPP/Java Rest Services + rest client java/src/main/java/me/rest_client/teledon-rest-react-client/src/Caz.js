import React from  'react';
import './App.css'

class TableRow extends React.Component{

    handle = (_) => {
        console.log('delete button pentru '+this.props.caz.descriere);
        this.props.deleteF(this.props.caz.id);
    }

    render() {
        return (
            <tr>
                <td>{this.props.caz.id}</td>
                <td>{this.props.caz.description}</td>
                <td><button  onClick={this.handle}>Delete</button></td>
            </tr>
        );
    }
}

class Tabel extends React.Component {
    render() {
        var rows = [];
        var functieStergere = this.props.deleteF;
        this.props.cazuri.forEach(function(caz) {
            rows.push(<TableRow caz={caz} key = {caz.id} deleteF = {functieStergere} />);
        });
        return (<div className="CazTable">

                <table className="center">
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Descriere</th>

                        <th></th>
                    </tr>
                    </thead>
                    <tbody>{rows}</tbody>
                </table>

            </div>
        );
    }
}

export default Tabel;