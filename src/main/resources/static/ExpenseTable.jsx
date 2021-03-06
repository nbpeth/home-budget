import React from 'react';
import Expense from './Expense.jsx';
import store from './expensesStore.js';
import { loadDataAction, loadStatsAction } from './actions.js';

class ExpenseTable extends React.Component {
    constructor(props){
        super(props);
    }
   
    componentDidMount(){
        store.dispatch(loadDataAction());
    }
    
    render() {    
        var rows = []; 
        var expenses = store.getState().expenses.content;

        if(expenses && expenses.length > 0){
            var n=1;
            expenses.forEach((expense) => {
                rows.push(<Expense expense={expense} key={n++}/>);
            });
        }   
        return(
            <div>
            <Pagination />
            <table className="table table-striped table-bordered table-hover" id="expenseTable">
                <thead className="bg-info">
                    <tr>
                        <td><h3>Location</h3></td>
                        <td><h3>Cost</h3></td>
                        <td><h3>Type</h3></td>
                        <td><h3>Date</h3></td>
                        <td><h3></h3></td>
                    </tr>
                    
                </thead>
                <tbody>
                    {rows}
                </tbody>
            </table>
            <Pagination />
            </div>
        );
    }
};

class Pagination extends React.Component {
    constructor(props){
        super(props);
    }

    change(page){
        store.dispatch(loadDataAction(page));
    }
    
    render() {    
        var expenses = store.getState().expenses;
        var currentPage = expenses.number;
        var lastPage = expenses.totalPages;
        var previousButton = null;
        var nextButton = null;

        if(currentPage > 0){
            previousButton = <button className="btn-link" onClick={() => {this.change(currentPage-1)}}>Previous</button>
        } 

        if(currentPage < lastPage){
            nextButton = <button className="btn-link" onClick={() => {this.change(currentPage+1)}}>Next</button>
        }

        return(
            <table className="table" id="pagination">
                <thead className="bg-info">
                    <tr>
                        <td>
                             <div className="row">
                                <div className="col-md-6 text">{previousButton}</div>
                                <div className="col-md-6 text-right">{nextButton}</div>
                            </div>
                        </td>
                    </tr>
                </thead>
            </table>
            /*<table className="table" id="pagination">
                <thead className="bg-info">
                    <tr>
                        <td>    
                            <div className="text-center">
                                <ul className="pagination">
                                    <li className="page-item">{previousButton}</li>
                                    <li className="page-item"><a href="#" className="page-link">1</a></li>
                                    <li className="page-item"><a href="#" className="page-link">2</a></li>
                                    <li className="page-item"><a href="#" className="page-link">3</a></li>
                                    <li className="page-item"><a href="#" className="page-link">4</a></li>
                                    <li className="page-item"><a href="#" className="page-link">5</a></li>
                                    <li className="page-item">{nextButton}</li>
                                </ul>
                            </div>
                        </td>
                    </tr>
                </thead>
            </table>*/
        );
    }
};

export default ExpenseTable;