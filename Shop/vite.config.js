import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue(), vueJsx()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    host: 'localhost', //本机地址
    port: 8080, //前端端口
    // 反向代理
    proxy: {
      '/api':{
        target: "http://localhost:9090",
        changeOrigin: true,
        pathRewrite: {
          '^/api': '/api' // 需要rewrite的,
        }
      }
    }
  },
  optimizeDeps: {
    include: ['url']
  }
})

