import Router from 'koa-router'
import tractoareStore from './store'
import {broadcast} from "../utils"

export const router = new Router();

router.get('/', async (ctx) => {
    const response = ctx.response;
    const userId = ctx.state.user._id;
    response.body = await tractoareStore.find({userId});
    response.status = 200;

});

router.get('/all', async (ctx) => {
    const response = ctx.response;
    const userId = ctx.state.user._id;
    response.body = await tractoareStore.find({userId});
    response.status = 200;
})

router.get('/culori', async (ctx) => {
    const culori = await tractoareStore.find({}).then(r => {
        let a = r.map(t => t.culoare)
        return [...new Set(a)];
    })
    const response = ctx.response
    response.body = JSON.stringify({culori: culori})
    response.store = 200
})

router.get('/filter/:fil', async (ctx) => {
    const url = require('url');
    const numItems = url.parse(ctx.request.url, true).query.numItems
    const pageCnt = url.parse(ctx.request.url, true).query.pageCnt
    const fil = ctx.params.fil

    const response = ctx.response;
    const userId = ctx.state.user._id;

    await tractoareStore.find({userId}).then(r => {
        response.body = r.filter(t => t.culoare === fil).slice(pageCnt * numItems, parseInt(pageCnt) * parseInt(numItems) + parseInt(numItems))
        response.status = 200
    })

})

router.get('/page', async (ctx) => {
    const url = require('url');
    const numItems = url.parse(ctx.request.url, true).query.numItems
    const pageCnt = url.parse(ctx.request.url, true).query.pageCnt
    const response = ctx.response;
    const userId = ctx.state.user._id;
    console.log(numItems)
    console.log(pageCnt)

    await tractoareStore.find({userId}).then(r => {
        response.body = r.slice(pageCnt * numItems, parseInt(pageCnt) * parseInt(numItems) + parseInt(numItems))
        response.status = 200
    })

})

router.get('/:id', async (ctx) => {
    const userId = ctx.state.user._id;
    const tractor = await tractoareStore.findOne({_id: ctx.params.id});
    const response = ctx.response;

    if (tractor) {
        if (tractor.userId === userId) {
            response.body = tractor
            response.status = 200
        } else {
            response.status = 403
        }
    } else {
        response.status = 404
    }
});

const createTractor = async (ctx, tractor, response) => {
    try {
        const userId = ctx.state.user._id;
        tractor.userId = userId;
        console.log('crreare tractor primit')
        console.log(tractor)
        tractor.createdOn = new Date().toString()
        tractor.version = 0
        tractor.conflict = false
        console.log(tractor)
        const tractor_i = await tractoareStore.insert(tractor);
        response.body = tractor_i;
        response.status = 201; // created
        broadcast(userId, {type: 'created', payload: tractor_i});
    } catch (err) {
        response.body = {message: err.message}
        response.status = 400
    }
};

router.post('/', async ctx => await createTractor(ctx, ctx.request.body, ctx.response));

router.put('/:id', async (ctx) => {
    const tractor = ctx.request.body
    const id = ctx.params.id;
    const tractorId = tractor._id;
    const response = ctx.response;

    if (tractorId && tractorId !== id) {
        response.body = {message: 'Param id and body _id should be the same'};
        response.status = 400;
        return
    }
    if (!tractorId) {
        console.log('suntem in put si facem create')
        await createTractor(ctx, tractor, response);
    } else {
        let tr_check = await tractoareStore.find({_id: id}).then(r => {

            return r[0]
        })
        console.log("dupa find")
        console.log(tr_check)
        console.log("tractorul primitL:")
        console.log(tractor)
        if (tr_check.version === tractor.version) {
            console.log("CONFLICT")
            const userId = ctx.state.user._id;
            tractor.conflict = true
            response.status = 200
            response.body = tractor
            broadcast(userId, {type: 'conflict', payload: tractor})
        } else {
            tractor.conflict = false
            tractor.userId = ctx.state.user._id;
            const updateCount = await tractoareStore.update({_id: id}, tractor)
            if (updateCount === 1) {
                response.body = tractor;
                response.status = 200
                const userId = ctx.state.user._id;
                broadcast(userId, {type: 'updated', payload: tractor})
            } else {
                response.body = {message: 'ressource no longer exists'}
                response.status = 405;
            }
        }
    }
});

router.del('/:id', async (ctx) => {
    const userId = ctx.state.user._id;
    const tractor = await tractoareStore.findOne({_id: ctx.params.id})
    if (tractor && userId !== tractor.userId) {
        ctx.response.status = 403;
    } else {
        await tractoareStore.remove({_id: ctx.params.id});
        ctx.response.status = 204;
        broadcast(userId, {type: 'deleted', payload: tractor})
    }
});

