import dataStore from 'nedb-promise'

export class TractoareStore {
    constructor({filename, autoload}) {
        this.store = dataStore({filename, autoload})
    }

    async getCulori() {
        let culori = []
        const all = this.store.find({}).then(r => {
            let a = r.map(t=>t.culoare)
            culori= [...new Set(a)];

            //culori = r.map(t => {if (culori.findIndex(t.culoare) == -1) return t.culoare})
        })
        //console.log(all)
        //for(const i in all)
        //console.log(culori)
        return culori
    }

    async find(props) {
        return this.store.find(props)
    }

    async findOne(props){
        return this.store.findOne(props)
    }

    async insert(tractor){
        let title = tractor.title
        if (!title){
            throw new Error("Missing title property")
        }
        return this.store.insert(tractor)
    }

    async update(props, tractor) {
        return this.store.update(props, tractor)
    }

    async remove(props) {
        return this.store.remove(props)
    }
}

export default new TractoareStore({filename: './db/tractoare.json', autoload: true})