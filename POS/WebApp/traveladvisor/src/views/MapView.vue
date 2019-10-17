<template>
  <div class="home">
     <Map :width="'100%'" :height="'800px'"/>
  </div>
</template>

<script>
import Map from '@/components/Map'

export default {
  name: 'home',
  components: {
    Map
  }
}
</script>